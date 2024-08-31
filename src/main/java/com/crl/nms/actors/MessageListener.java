<<<<<<< HEAD
package com.crl.nms.actors;

import akka.actor.ActorRef;
import com.crl.nms.common.utilities.Global;
import com.crl.nms.messages.*;
import com.crl.nms.pojo.CPUConfigPojo;
import com.crl.nms.pojo.RAMConfigPojo;
import com.crl.nms.service.DbHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import java.util.Date;

@Configuration

public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    DbHandlerService dbHandlerService;

    ObjectMapper objectMapper=new ObjectMapper();

    @KafkaListener(topics = Global.SYSTEM_INFO, groupId = "systeminformation",containerFactory = "kafkaListenerContainerFactory")
    public void listenGroup(String message) {

        Date dt=new Date();
        logger.info("Received Message  Device at  " +dt+":  " + message);
        try {
            SystemInfo systemInfo=objectMapper.readValue(message, SystemInfo.class);
            logger.info("systemInfo data recieved from "+systemInfo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(systemInfo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(systemInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.END_POINT_INTERFACE_DETAILS, groupId = "endPointInterfaceDetails",containerFactory = "kafkaListenerContainerFactory")
    public void listenEndPointDetails(String message) {

        Date dt=new Date();
        logger.info("Received Message  Device at  " +dt+":  " + message);
        try {
            EndPointInterfaceDetailInfo endPointInterfaceDetailInfo=objectMapper.readValue(message, EndPointInterfaceDetailInfo.class);
            logger.info("systemInfo data received from "+endPointInterfaceDetailInfo.getEndpoint_ip());
            ActorRef actorAddress=Global.deviceip_actoraddress.get(endPointInterfaceDetailInfo.getEndpoint_ip());
            if(actorAddress!=null){
                actorAddress.tell(endPointInterfaceDetailInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.PROCESS_INFO, groupId = "processinformation1",containerFactory = "kafkaListenerContainerFactory")
    public void listenProcessGroup(String message) {

        logger.info("Received Process Info Message  Device: " + message);
        try {
            ProcessInfo processInfo=objectMapper.readValue(message, ProcessInfo.class);
            logger.info("processInfo data recieved from "+processInfo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(processInfo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(processInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.ADD_PROCESS, groupId = "addprocessinfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenAddProcessGroup(String message) {

        logger.info("Received Message  Device: " + message);
        try {
            ProcessInfo processInfo=objectMapper.readValue(message, ProcessInfo.class);
            logger.info("processInfo data recieved from "+processInfo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(processInfo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(processInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.DELETE_PROCESS, groupId = "deleteprocessinfo",containerFactory = "kafkaListenerContainerFactory")
    public void listendeleteProcessGroup(String message) {

        logger.info("Received Message  Device: " + message);
        try {
            ProcessInfo processInfo=objectMapper.readValue(message, ProcessInfo.class);
            logger.info("processInfo data recieved from "+processInfo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(processInfo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(processInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.EDIT_PROCESS, groupId = "editprocessinfo",containerFactory = "kafkaListenerContainerFactory")
    public void listeneditProcessGroup(String message) {

        logger.info("Received Message  Device: " + message);
        try {
            ProcessInfo processInfo=objectMapper.readValue(message, ProcessInfo.class);
            logger.info("processInfo data recieved from "+processInfo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(processInfo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(processInfo,ActorRef.noSender());
            }
            else {
                logger.info("System is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.GET_ENDPOINT_CLIENT_CONFIG, groupId = "getendpointclientconfig",containerFactory = "kafkaListenerContainerFactory")
    public void listenEndPointClientConfigGroup(String message) {

        logger.info("Received Process Info Message  Device: " + message);
        try {
            EndPointClientConfigMsg endPointClientConfig=objectMapper.readValue(message, EndPointClientConfigMsg.class);
            logger.info("endPointClientConfig data recieved from "+endPointClientConfig.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(endPointClientConfig.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(endPointClientConfig,ActorRef.noSender());
            }
            else {
                logger.info("endPointClientConfig is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.ADD_RAM_CONFIG, groupId = "addramconfiginfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenAddRamConfigGroup(String message) {

        logger.info("Received Message  Device: " + message);
        try {
            RAMConfigPojo ramConfigPojo=objectMapper.readValue(message, RAMConfigPojo.class);
            logger.info("ramConfigInfo data recieved from "+ramConfigPojo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(ramConfigPojo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(ramConfigPojo,ActorRef.noSender());
            }
            else {
                logger.info("RAM Config is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.ADD_CPU_CONFIG, groupId = "addpcpuconfiginfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenAddCpuConfigGroup(String message) {

        logger.info("Received Message  Device: " + message);
        try {
            CPUConfigPojo cpuConfigPojo=objectMapper.readValue(message, CPUConfigPojo.class);
            logger.info("cpuConfigInfo data recieved from "+cpuConfigPojo.getIpAddress());
            ActorRef actoraddress=Global.deviceip_actoraddress.get(cpuConfigPojo.getIpAddress());
            if(actoraddress!=null){
                actoraddress.tell(cpuConfigPojo,ActorRef.noSender());
            }
            else {
                logger.info("CPU Config is not registered in database, Please contact to administrator");
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.ADD_DEVICE, groupId = "deviceAddInfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenDeviceAddInfo(String message) throws JsonProcessingException {

        AddDeviceModel addDevice = objectMapper.readValue(message,AddDeviceModel.class);
        dbHandlerService.createNewActor(addDevice.getNeKey(), dbHandlerService);
        logger.info("Device ADDED : " + message);
    }

    @KafkaListener(topics = Global.DELETE_DEVICE, groupId = "deviceDeleteInfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenDeviceDeleteInfo(String message) throws JsonProcessingException {

        AddDeviceModel addDevice = objectMapper.readValue(message,AddDeviceModel.class);
        dbHandlerService.deleteActor(addDevice.getDeviceIP());
        logger.info("Device DELETED : " + message);
    }

    @KafkaListener(topics = Global.EDIT_DEVICE, groupId = "deviceEditInfo",containerFactory = "kafkaListenerContainerFactory")
    public void listenDeviceEditInfo(String message) throws JsonProcessingException {

        AddDeviceModel addDevice = objectMapper.readValue(message,AddDeviceModel.class);
        dbHandlerService.deleteActor(addDevice.getDeviceIP());
        dbHandlerService.createNewActor(addDevice.getNeKey(), dbHandlerService);
        logger.info("Device EDITED : " + message);
    }

    @KafkaListener(topics = Global.process_health_status, groupId = "ClientProcessInfo",containerFactory = "kafkaListenerContainerFactory")
    public void handleDeviceEditInfo(String message) throws JsonProcessingException {

        var req = objectMapper.readValue(message,ProcessHealthStatusModel.class);
        if (!req.getService().equals(Global.process) || req.getDeviceIp() == null) {
            return;
        }

        var deviceIp = req.getDeviceIp();

        var actorAddress = Global.deviceip_actoraddress.get(deviceIp);
        if (actorAddress == null) {
            return;
        }

        var processInfo = new ProcessInfo();
        
        processInfo.setIpAddress(deviceIp);
        processInfo.setMessage_type(Global.PROCESS_INFO_DOWN);

        actorAddress.tell(processInfo, ActorRef.noSender());

        logger.info("ProcessInfo Down in " + req.getDeviceIp());
    }
}
=======
package com.crl.nms.actors;


import com.crl.nms.common.utilities.Global;

import com.crl.nms.configuration.DbConfigProperties;
import com.crl.nms.messages.CronMessage;
import com.crl.nms.service.DbHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.io.IOException;

@EnableKafka
@Configuration
@Service
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    DbHandlerService dbHandlerService;


    ObjectMapper objectMapper=new ObjectMapper();

    @Autowired
    private DbConfigProperties dbConfigProperties;
    private String backupDir;

    @PostConstruct
    public void init() {
        this.backupDir = dbConfigProperties.getBackupDir();
    }
    @KafkaListener(topics = Global.CREATE_BACKP, groupId = "createbackup",containerFactory = "kafkaListenerContainerFactory")
    public void listenGroup(String message) {
        Date dt=new Date();

        logger.info("Received Message Of Db backup  "+":  " + message);
        try {
            // Parse the backup time from the message
            JsonNode jsonNode = objectMapper.readTree(message);
            String backupTimeStr = jsonNode.get("backupTime").asText();
            LocalDateTime backupTime = LocalDateTime.parse(backupTimeStr, DateTimeFormatter.ISO_DATE_TIME);

            // Calculate delay
            long delay = Duration.between(LocalDateTime.now(), backupTime).toMillis();
            if (delay > 0) {
                // Schedule the backup
                dbHandlerService.scheduleBackup(delay);
                logger.info("Backup scheduled for: {}", backupTime);
            } else {
                logger.error("Backup time is in the past: {}", backupTime);
            }
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse message", e);
        }

    }

    @KafkaListener(topics = Global.GET_DBBACKUP_LIST, groupId = "getdbbackuplist",containerFactory = "kafkaListenerContainerFactory")
    public void listenForDbBackupList(String message) {
        Date dt=new Date();
        logger.info("Received Message Of Db backup list "+":  " + message);
        try {
            dbHandlerService.sendBackupDirectoryContentToKafka(backupDir);
            dbHandlerService.sendMsgToKAFKA("takebackuplist","DatabaseBackup list sent");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
>>>>>>> ce7b643 (CBTC_DB_BACKUP)
