
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
import com.crl.nms.messages.ImportingMessage;
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
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
       // sendBackupDirectoryContentToKafka(backupDir);
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
            logger.info("DataBase backup Completed Successfully !!!");
            sendBackupDirectoryContentToKafka(backupDir);

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
           // ListOfFileInDataBaseBackupDirectory listOfFileInBackupDir = new ListOfFileInDataBaseBackupDirectory(fileContents);
            String jsonMessage = objectMapper.writeValueAsString(fileContents);
            logger.info(jsonMessage);
            kafkaJSONStringMsgSender.send("backup_file_list", jsonMessage); // Specify your Kafka topic
           // logger.info("Published backup directory content to Kafka");
            logger.info("{} : Published backup directory content to Kafka successfully on topic: backup_file_list ",jsonMessage);

        } catch (Exception e) {
            logger.error("Failed to send backup directory content to Kafka", e);
        }
    }
    private List<DataBaseBackupDirectoryContent> getBackupDirectoryContentsOld(String backupDir) {
        List<DataBaseBackupDirectoryContent> fileContents = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(backupDir))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                    String fileName = path.getFileName().toString();
                    String dateOfFileCreation = attrs.creationTime().toString().trim(); // ISO format
                    System.out.println("Date of File Creation "+dateOfFileCreation);
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


    public List<DataBaseBackupDirectoryContent> getBackupDirectoryContentsold1(String backupDir) {
        List<DataBaseBackupDirectoryContent> fileContents = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(backupDir))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

                    // Get file name
                    String fileName = path.getFileName().toString();

                    // Format creation date to dd-MM-yyyy HH:mm:ss
                   /* Date creationDate = new Date(attrs.creationTime().toMillis());
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String dateOfFileCreation = dateFormatter.format(creationDate);*/


                    //Get creation time
                    FileTime creationTime=attrs.creationTime();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date dateOfFileCreation=new Date(creationTime.toMillis());

                    // Convert size to MB
                    double fileSizeInMb = attrs.size() / (1024.0 * 1024.0);
                    String fileSize = String.format("%.2f MB", fileSizeInMb);

                    System.out.println("Date of File Creation: " + dateOfFileCreation);
                    System.out.println("File Size: " + fileSize);

                    // Create content object and add to list
                    DataBaseBackupDirectoryContent content = new DataBaseBackupDirectoryContent(fileName, sdf.format(dateOfFileCreation), fileSize);
                    fileContents.add(content);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read directory contents", e);
        }
        return fileContents;
    }
    public List<DataBaseBackupDirectoryContent> getBackupDirectoryContents(String backupDir) {
        List<DataBaseBackupDirectoryContent> fileContents = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(backupDir))) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

                    // Get file name
                    String fileName = path.getFileName().toString();

                    // Format creation date to dd-MM-yyyy HH:mm:ss
                    FileTime creationTime = attrs.creationTime();
                    FileTime modifiedTime = attrs.lastModifiedTime();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    // Set the timezone to the default system timezone
                    dateFormatter.setTimeZone(TimeZone.getDefault());
                    String dateOfFileCreation = dateFormatter.format(new Date(creationTime.toMillis()));
                    String dateOfFileModification = dateFormatter.format(new Date(modifiedTime.toMillis()));
                    // Convert size to MB
                    double fileSizeInMb = attrs.size() / (1024.0 * 1024.0);
                    String fileSize = String.format("%.2f MB", fileSizeInMb);

                    System.out.println("Date of File Creation: " + dateOfFileCreation);
                    System.out.println("Date of File Modification: " + dateOfFileModification);
                    System.out.println("File Size: " + fileSize);

                    // Create content object and add to list
                    DataBaseBackupDirectoryContent content = new DataBaseBackupDirectoryContent(fileName, dateOfFileModification, fileSize);
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
            logger.info("Message {} :Published successfully to Kafka topic: {}",message, topicName);
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

    public void dbImport(ImportingMessage importingMessage) {

        // Read configuration from DbConfigProperties
        String backupDir = dbConfigProperties.getBackupDir();
        String dbHost = dbConfigProperties.getHost();
        String dbPort = dbConfigProperties.getPort();
        String dbName = dbConfigProperties.getName();
        String dbUser = dbConfigProperties.getUser();
        String dbPassword = dbConfigProperties.getPassword();


        try {
          String  backupFilePath=importingMessage.getFileName();
            // Constructing the pg_restore command
            String command = String.format(
                    "pg_restore -h %s -p %s -U %s -d %s -v %s",
                    dbHost, dbPort, dbUser, dbName, backupFilePath
            );

            // Set environment variable for password
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.environment().put("PGPASSWORD", dbPassword);

            // Execute the command
            Process process = processBuilder.start();

            // Reading the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Waiting for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup restored successfully!");
            } else {
                System.err.println("Backup restoration failed!");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


