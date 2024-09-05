
/*
package com.crl.nms.service;

import com.crl.nms.common.utilities.Global;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

*/
/**
 *
 * @author acer
 *//*

@Service
@EnableKafka
@Configuration
public class DbHandlerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaJSONStringMsgSender;

    @Autowired
    private ObjectMapper objectMapper;

    // Database configuration
    private static final String containerName = "postgres-test-15"; // Docker container name
    private static final String dbHost = "192.168.110.33"; // Database host inside Docker
    private static final String dbPort = "5434"; // Default PostgreSQL port
    private static final String dbName = "nmsdb";
    private static final String dbUser = "postgres";
    private static final String dbPassword = "postgres";
    private static final String backupDir = "/home/DatabaseBackup/"; // Local backup directory




    private static final Logger logger = LoggerFactory.getLogger(DbHandlerService.class);

//    @Autowired
//    KafkaTemplate<String, String> kafkaJSONStringMsgSender;


    public static void createBackup() throws IOException {
        // Ensure backup directory exists
        Path backupDirPath = Paths.get(backupDir);

        // Check if the directory exists
        if (!Files.isDirectory(backupDirPath)) {
            // Directory does not exist, attempt to create it
            try {
                Files.createDirectories(backupDirPath);
                logger.info("Backup directory created: " + backupDir);
            } catch (AccessDeniedException e) {
                logger.error("Access denied when trying to create backup directory: " + backupDir, e);
                throw new IOException("Access denied for creating backup directory: " + backupDir, e);
            } catch (IOException e) {
                logger.error("Failed to create backup directory: " + backupDir, e);
                throw new IOException("Backup directory does not exist and failed to create: " + backupDir, e);
            }
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String backupFileName = String.format("backup_%s.backup", timestamp);
        //String containerBackupPath = String.format("/home/crl/DatabaseBackup/%s", backupFileName);
        String containerBackupPath = String.format("%s/%s", backupDir, backupFileName);

        // Construct Docker exec command to perform the backup inside the container
        String dockerExecCommand = String.format(
                "PGPASSWORD=%s pg_dump -h %s -p %s -U %s -F c -b -v -f %s %s",

                dbPassword,
                dbHost,
                dbPort,
                dbUser,
                containerBackupPath,
                dbName
        );

        // Construct Docker cp command to copy the backup from the container to the host
//        String dockerCpCommand = String.format(
//                "docker cp %s:%s %s",
//                containerName,
//                containerBackupPath,
//                localBackupPath
//        );

  */
/*      try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", dockerExecCommand});
            process.waitFor();
            Process process1 = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", dockerCpCommand});
            process1.waitFor();
            // Optionally handle output and errors
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*//*


        try {
            // Execute Docker exec command
            executeCommand(dockerExecCommand);

            // Execute Docker cp command
            //executeCommand(dockerCpCommand);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new IOException("Backup process failed", e);
        }
    }

    private static void executeCommand(String command) throws IOException, InterruptedException {

        logger.info(command);
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // Read and log the output from the command
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println("OUTPUT: " + s);
            }

            // Read and log any errors from the command
            while ((s = stdError.readLine()) != null) {
                System.err.println("ERROR: " + s);
            }
        }

        process.waitFor(); // Ensure the process completes
        if (process.exitValue() != 0) {
            throw new IOException("Command failed with exit code " + process.exitValue());
        }
    }

    public void sendMsgToKAFKA( String topicname,Object messages) {

        try {
            String message = objectMapper.writeValueAsString(messages);
            kafkaJSONStringMsgSender.send(topicname, message);
            logger.info("Sent Successfully");
        } catch (Exception e) {
            logger.error("Failed to send message: ", e);
        }
    }
}*/






package com.crl.nms.service;

import com.crl.nms.common.utilities.Global;
import com.crl.nms.configuration.DbConfigProperties;
import com.crl.nms.messages.CronMessage;
import com.crl.nms.pojo.DataBaseBackupDirectoryContent;
import com.crl.nms.pojo.ListOfFileInDataBaseBackupDirectory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@EnableKafka
@Configuration
public class DbHandlerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaJSONStringMsgSender;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DbConfigProperties dbConfigProperties;
    private final TaskScheduler taskScheduler;
    @Autowired
    public DbHandlerService(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }
    private static final Logger logger = LoggerFactory.getLogger(DbHandlerService.class);

    //Global.cronMessage;

    @Scheduled(cron = "0 0 0 * * *")
    public void createBackup() throws IOException {
        // Read configuration from DbConfigProperties
        String backupDir = dbConfigProperties.getBackupDir();
        String dbHost = dbConfigProperties.getHost();
        String dbPort = dbConfigProperties.getPort();
        String dbName = dbConfigProperties.getName();
        String dbUser = dbConfigProperties.getUser();
        String dbPassword = dbConfigProperties.getPassword();

        Path backupDirPath = Paths.get(backupDir);

        if (!Files.isDirectory(backupDirPath)) {
            try {
                Files.createDirectories(backupDirPath);
                logger.info("Backup directory created: " + backupDir);
            } catch (AccessDeniedException e) {
                logger.error("Access denied when trying to create backup directory: " + backupDir, e);
                throw new IOException("Access denied for creating backup directory: " + backupDir, e);
            } catch (IOException e) {
                logger.error("Failed to create backup directory: " + backupDir, e);
                throw new IOException("Backup directory does not exist and failed to create: " + backupDir, e);
            }
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String backupFileName = String.format("backup_%s.backup", timestamp);
        String containerBackupPath = String.format("%s/%s", backupDir, backupFileName);

        String dockerExecCommand = String.format(
                "PGPASSWORD=%s pg_dump -h %s -p %s -U %s -F c -b -v -f %s %s",
                dbPassword,
                dbHost,
                dbPort,
                dbUser,
                containerBackupPath,
                dbName
        );

        try {
            executeCommand(dockerExecCommand);

        } catch (IOException | InterruptedException e) {
            logger.error("Backup process failed", e);
            throw new IOException("Backup process failed", e);
        }
    }

    private void executeCommand(String command) throws IOException, InterruptedException {
        logger.info(command);
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String s;
            while ((s = stdInput.readLine()) != null) {
                logger.info("OUTPUT: " + s);
            }
        }

        process.waitFor();
        if (process.exitValue() != 0) {
            throw new IOException("Command failed with exit code " + process.exitValue());
        }
    }
    public void sendBackupDirectoryContentToKafka(String backupDir) {
        try {
            List<DataBaseBackupDirectoryContent> fileContents = getBackupDirectoryContents(backupDir);
            ListOfFileInDataBaseBackupDirectory listOfFileInBackupDir = new ListOfFileInDataBaseBackupDirectory(fileContents);
            String jsonMessage = objectMapper.writeValueAsString(listOfFileInBackupDir);
            logger.info(jsonMessage);
            kafkaJSONStringMsgSender.send("backupFileDetails", jsonMessage); // Specify your Kafka topic
            logger.info("Published backup directory content to Kafka");
        } catch (Exception e) {
            logger.error("Failed to send backup directory content to Kafka", e);
        }
    }
    private List<DataBaseBackupDirectoryContent> getBackupDirectoryContents(String backupDir) {
        List<DataBaseBackupDirectoryContent> fileContents = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(backupDir))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                    String fileName = path.getFileName().toString();
                    String dateOfFileCreation = attrs.creationTime().toString().trim(); // ISO format
                    String fileSize = String.valueOf(attrs.size());

                    DataBaseBackupDirectoryContent content = new DataBaseBackupDirectoryContent(fileName, dateOfFileCreation, fileSize);
                    fileContents.add(content);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read directory contents", e);
        }
        return fileContents;
    }

    public void sendMsgToKAFKA(String topicName, Object messages) {
        try {
            String message = objectMapper.writeValueAsString(messages);
            kafkaJSONStringMsgSender.send(topicName, message);
            logger.info("Published successfully to Kafka topic: {}", topicName);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka topic: {}", topicName, e);
        }
    }

    public void scheduleBackupBasedOnTime(String timeString) throws Exception {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime backupTime = LocalTime.parse(timeString, timeFormatter);
        LocalTime now = LocalTime.now();

        // Calculate delay until the specified time
        long delay = backupTime.toSecondOfDay() - now.toSecondOfDay();
        if (delay < 0) {
            // If the time has already passed for today, schedule for the next day
            delay += 24 * 60 * 60; // seconds in a day
        }

        logger.info("Scheduling backup for {} seconds from now", delay);

        taskScheduler.schedule(() -> {
            try {
                createBackup();
            } catch (IOException e) {
                logger.error("Backup process failed", e);
            }
        }, new Date(System.currentTimeMillis() + delay * 1000));
    }
}


