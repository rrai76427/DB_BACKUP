<<<<<<< HEAD
package com.crl.nms.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import com.crl.nms.Repository.*;
import com.crl.nms.actors.ConsumerActor;
import com.crl.nms.actors.DeviceActor;
import com.crl.nms.common.utilities.Global;
import com.crl.nms.databases.*;
import com.crl.nms.messages.*;
import com.crl.nms.pojo.CPUConfigPojo;
import com.crl.nms.pojo.NmsNeCpuTh;
import com.crl.nms.pojo.NmsNeRamTh;
import com.crl.nms.pojo.RAMConfigPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author acer
 */
@Service
public class DbHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(DbHandlerService.class);
    @Autowired
    NmsNeProcessRepository nmsNeProcessRepository;

    @Autowired
    NmsNeHddRepository nmsNeHddRepository;

    @Autowired
    NmsNeCpuRepository nmsNeCpuRepository;

    @Autowired
    NmsNeRamRepository nmsNeRamRepository;

    @Autowired
    NmsNeDeviceStatsRepo nmsNeDeviceStatsRepo;

    @Autowired
    NmsNeDetailRepository NmsNeDetailRepository;

    @Autowired
    NmsAlarmsRepository nmsAlarmsRepository;

    @Autowired
    CPUConfigRepository cpuConfigRepository;

    @Autowired
    RAMConfigRepository ramConfigRepository;

    @Autowired
    NmsAlarmClassificationRepository nmsAlarmClassificationRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaJSONStringMsgSender;

    @Autowired
    ActorSystem actorSystem;

    @Autowired
    NmsNeDeviceInterfaceDetailRepo nmsNeDeviceInterfaceDetailRepo;

    public void initiateActor(DbHandlerService dbHandlerService) {

        try {
            logger.info("Consumer Actor");
            // ActorRef webSocketClientActor =
            // actorSystem.actorOf(ConsumerActor.props(actorSystem,dbHandlerService));
            List<NmsNeDetail> nmsNeDetail = NmsNeDetailRepository.findAll();
            List<NmsNeProcesses> nmsNeProcesses;
            for (NmsNeDetail device : nmsNeDetail) {
                nmsNeProcesses = nmsNeProcessRepository.findByNmsNeProcessesPKNekey(device.getNekey());
                ActorRef deviceaddress = actorSystem.actorOf(
                        DeviceActor.props(device.getNeIp(), device.getNekey(), device.getNodeKey().getNodeKey(),
                                device.getNodeId(), nmsNeProcesses, dbHandlerService, device));
                com.crl.nms.common.utilities.Global.deviceip_actoraddress.put(device.getNeIp(), deviceaddress);
            }
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertRAMInfo(NmsNeRam nmsNeRam) {

        nmsNeRamRepository.save(nmsNeRam);

    }

    public void insertCPUInfo(NmsNeCpu nmsNeCpu) {

        nmsNeCpuRepository.save(nmsNeCpu);
    }

    public void insertHDDInfo(NmsNeHdd nmsNeHdd) {

        nmsNeHddRepository.save(nmsNeHdd);
    }

    public void sendProcNames(String nekey, String neip) {

        List<NmsNeProcesses> processesList = nmsNeProcessRepository.findByNmsNeProcessesPKNekey(nekey);
        for (NmsNeProcesses nmsNeProcesses : processesList) {
            nmsNeProcesses.setNmsNeDetail(null);
            nmsNeProcesses.setNodeKey(null);
        }
        ProcessInfo processInfo = ProcessInfo.builder().message_type("PROC_DETAIL").processes_list(processesList)
                .build();
        sendMsgToKAFKA(nekey, processInfo, "machine_info" + neip);
    }

    public NmsAlarmClassification alarmClassificationForProcesseDown() {

        NmsAlarmClassification nmsAlarmClassification = nmsAlarmClassificationRepository
                .findByAlarmId(Global.PROCESS_DOWN_ALARM_ID);
        return nmsAlarmClassification;
    }

    public void insertProcessInfo(NmsNeProcesses nmsNeProcesses) {

        Optional<NmsNeProcesses> nmsNeProcesses1 = nmsNeProcessRepository
                .findByNmsNeProcessesPK(nmsNeProcesses.getNmsNeProcessesPK());
        if (nmsNeProcesses1.isPresent()) {
            // nmsNeProcesses1.get().setProccksum(nmsNeProcesses.getProccksum());
            // nmsNeProcesses1.get().setCpuPercent(nmsNeProcesses.getCpuPercent());
            // nmsNeProcesses1.get().setIoreadBytes(nmsNeProcesses.getIoreadBytes());
            // nmsNeProcesses1.get().setIowriteBytes(nmsNeProcesses.getIowriteBytes());
            // nmsNeProcesses1.get().setMemPercent(nmsNeProcesses.getMemPercent());
            // nmsNeProcesses1.get().setProccksum(nmsNeProcesses.getProccksum());
            // nmsNeProcesses1.get().setProcpath(nmsNeProcesses.getProcpath());
            // nmsNeProcesses1.get().setProcno(nmsNeProcesses.getProcno());
            // nmsNeProcesses1.get().setProcsize(nmsNeProcesses.getProcsize());
            // nmsNeProcesses1.get().setProcstate(nmsNeProcesses.getProcstate());
            // nmsNeProcesses1.get().setProcstatus(nmsNeProcesses.getProcstatus());
            // nmsNeProcesses1.get().setRunDuration(nmsNeProcesses.getRunDuration());
            // nmsNeProcessRepository.save(nmsNeProcesses1.get());

            nmsNeProcessRepository.updateProcessInfo(nmsNeProcesses.getCpuPercent(),
                    nmsNeProcesses.getMemPercent(),
                    nmsNeProcesses.getProcstate(),
                    nmsNeProcesses.getProcstatus(),
                    nmsNeProcesses.getNmsNeProcessesPK().getProcname());
        } else {
            nmsNeProcessRepository.save(nmsNeProcesses);
        }
    }

    public void sendProcessMsg(String nekey, String neip, ProcessInfo processInfo) {

        sendMsgToKAFKA(nekey, processInfo, "machine_info" + neip);
    }

    public void setProcessDownByNeIp(String neIp) {
        try {
            var device = NmsNeDetailRepository.findByNeIp(neIp);

            if (device.isEmpty()) {
                throw new Exception("IP does not exist");
            }
            nmsNeProcessRepository.updateProcessByNekey(Global.PROCESS_DOWN, Global.PROCESS_DOWN, device.getFirst().getNekey());
        } catch (Exception ex) {
            System.out.println("[DbHandlerService] " + ex.getMessage());
        }
    }

    public ProcessDownCount getProcessDownCnt(String nekey, short alarmId, String processName) {

        List<NmsAlarms> nmsAlarmsList = nmsAlarmsRepository
                .findByNekeyAndAlarmIdAlarmIdAndAlarmDescOrderByReceivingDateTimeDesc(nekey, alarmId,
                        processName + Global.PROCESS_DOWN_STR);
        if (nmsAlarmsList.size() > 0) {
            for (NmsAlarms nmsAlarms : nmsAlarmsList) {
                ProcessDownCount processDownCount = new ProcessDownCount();
                processDownCount.setProcessDownCount(nmsAlarms.getAlarmCount());
                processDownCount.setSendFlag(false);
                processDownCount.setTagNo(nmsAlarms.getTagno());
                return processDownCount;
            }
        } else {
            return ProcessDownCount.builder()
                    .tagNo(BigDecimal.ZERO)
                    .sendFlag(true)
                    .processDownCount(BigInteger.ZERO).build();
        }
        return ProcessDownCount.builder()
                .tagNo(BigDecimal.ZERO)
                .sendFlag(true)
                .processDownCount(BigInteger.ZERO).build();
    }

    public void sendMsgToKAFKA(String nekey, Object messages, String topicname) {

        try {
            ObjectMapper Obj = new ObjectMapper();
            String message = Obj.writeValueAsString(messages);
            ;
            kafkaJSONStringMsgSender.send(topicname, message);
            logger.info("Message Publis to Kafka !!!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void updateClearingTime(String nekey, short alarmId, String processName, short faultstatus) {

        List<NmsAlarms> nmsAlarmsList = nmsAlarmsRepository
                .findByNekeyAndAlarmIdAlarmIdAndAlarmDescOrderByReceivingDateTimeDesc(nekey, alarmId,
                        processName + Global.PROCESS_DOWN_STR);
        try {
            if (nmsAlarmsList.size() > 0) {
                Date dt = new Date();
                NmsAlarms nmsAlarms = nmsAlarmsList.get(0);
                if (faultstatus == Global.FAULT_AUTO_CLOSE)
                    nmsAlarms.setClearingDateTime(dt);
                nmsAlarms.setFaultStatus(faultstatus);
                nmsAlarmsRepository.save(nmsAlarms);
            } else {
                logger.info("Not Exist");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendEndPointConfigMsg(String neKey, String deviceIp) {

        Optional<RAMConfig> ramConfig = ramConfigRepository.findById(neKey);
        NmsNeRamTh nmsNeRamTh = new NmsNeRamTh();
        nmsNeRamTh.setRamTh(ramConfig.get().getThreshold());
        Optional<CPUConfig> cpuConfig = cpuConfigRepository.findById(neKey);
        NmsNeCpuTh nmsNeCpuTh = new NmsNeCpuTh();
        nmsNeCpuTh.setCpuThreshold(cpuConfig.get().getThreshold());
        EndPointClientConfigMsg endPointClientConfigMsg = new EndPointClientConfigMsg();
        endPointClientConfigMsg.setMessage_type("SET_ENDPOINT_CONFIG");
        endPointClientConfigMsg.setNmsNeCpuTh(nmsNeCpuTh);
        endPointClientConfigMsg.setNmsNeRamTh(nmsNeRamTh);
        sendMsgToKAFKA(neKey, endPointClientConfigMsg, "endpoint_config_info");
    }

    public void sendConfigMsg(String neKey, String replace, Object configObjectInfo) {

        if (configObjectInfo instanceof CPUConfigPojo) {
            CPUConfig cpuConfig = new CPUConfig();
            cpuConfig.setCurrentValue(((CPUConfigPojo) configObjectInfo).getCurrentValue());
            cpuConfig.setUpdatingTime(new Date());
            cpuConfigRepository.save(cpuConfig);
        } else if (configObjectInfo instanceof RAMConfigPojo) {
            RAMConfig ramConfig = new RAMConfig();
            ramConfig.setCurrentValue(((RAMConfigPojo) configObjectInfo).getCurrentValue());
            ramConfig.setUpdatingTime(new Date());
            ramConfigRepository.save(ramConfig);
        }
    }

    public void insertSystemInfo(NmsNeDeviceStats data) {

        if (nmsNeDeviceStatsRepo.existsById(data.getNmsNeDeviceStatsPK())) {
            nmsNeDeviceStatsRepo.deleteById(data.getNmsNeDeviceStatsPK());
        }
        nmsNeDeviceStatsRepo.save(data);
    }

    public Optional<NmsNeDeviceStats> getDeviceStatusField(NmsNeDeviceStatsPK nmsNeDeviceStatsPK) {

        return nmsNeDeviceStatsRepo.findById(nmsNeDeviceStatsPK);
    }

    public void insertEndpointInterfaceInfo(NmsNeDeviceInterfaceDetail nmsNeDeviceInterfaceDetail) {

        if (nmsNeDeviceInterfaceDetailRepo.existsById(nmsNeDeviceInterfaceDetail.getNmsNeDeviceInterfaceDetailPK())) {
            nmsNeDeviceInterfaceDetailRepo.deleteById(nmsNeDeviceInterfaceDetail.getNmsNeDeviceInterfaceDetailPK());
        }
        nmsNeDeviceInterfaceDetailRepo.save(nmsNeDeviceInterfaceDetail);
    }

    public void createNewActor(String neKey, DbHandlerService dbHandlerService) {

        Optional<NmsNeDetail> nmsNeDetail = NmsNeDetailRepository.findById(neKey);
        if (nmsNeDetail.isPresent()) {
            List<NmsNeProcesses> nmsNeProcesses = nmsNeProcessRepository.findByNmsNeProcessesPKNekey(neKey);
            ActorRef deviceAddress = actorSystem.actorOf(DeviceActor.props(nmsNeDetail.get().getNeIp(),
                    nmsNeDetail.get().getNekey(), nmsNeDetail.get().getNodeKey().getNodeKey(),
                    nmsNeDetail.get().getNodeId(), nmsNeProcesses, dbHandlerService, nmsNeDetail.get()));
            Global.deviceip_actoraddress.put(nmsNeDetail.get().getNeIp(), deviceAddress);
        }
    }

    public void deleteActor(String neIp) {

        ActorRef actorRef = Global.deviceip_actoraddress.get(neIp);
        actorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
        Global.deviceip_actoraddress.remove(neIp);
    }
}
=======
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

import com.crl.nms.configuration.DbConfigProperties;
import com.crl.nms.pojo.DataBaseBackupDirectoryContent;
import com.crl.nms.pojo.ListOfFileInDataBaseBackupDirectory;
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
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

    private static final Logger logger = LoggerFactory.getLogger(DbHandlerService.class);

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

    public void scheduleBackup(long delayMillis) {
        scheduler.schedule(() -> {
            try {
                createBackup();
            } catch (IOException e) {
                logger.error("Backup process failed", e);
            }
        }, delayMillis, TimeUnit.MILLISECONDS);
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
}

>>>>>>> ce7b643 (CBTC_DB_BACKUP)
