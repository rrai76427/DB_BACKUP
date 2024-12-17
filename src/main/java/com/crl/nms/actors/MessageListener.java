
package com.crl.nms.actors;

import com.crl.nms.common.utilities.Global;
import com.crl.nms.configuration.DbConfigProperties;
import com.crl.nms.messages.CronMessage;
import com.crl.nms.messages.ImportingMessage;
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
        /*try {
            // Parse the backup time from the message
            JsonNode jsonNode = objectMapper.readTree(message);

            CronMessage cronMessage=objectMapper.readValue(message, CronMessage.class);
            //Global.cronMessage=cronMessage;
            String backupTimeStr = cronMessage.getTime();
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
*/

        // Assuming message format is "HH:mm:ss" (24-hour time format)
        logger.info("Received schedule time from Kafka: {}", message);

        try {
            CronMessage cronMessage=objectMapper.readValue(message, CronMessage.class);

       /*     String backupTimeStr = cronMessage.getTime();
            LocalDateTime backupTime = LocalDateTime.parse(backupTimeStr, DateTimeFormatter.ISO_DATE_TIME)*/;
            if(cronMessage.getType().equals("AUTO")) {
                dbHandlerService.scheduleBackupBasedOnTime(cronMessage.getTime());
                dbHandlerService.sendMsgToKAFKA("alarm_notification","Auto DatabaseBackup Completed Successfully "+dt);
                logger.info("{} : Published successfully to Kafka topic: alarm_notification ",message);
            }
            else {
                dbHandlerService.createBackup();
                dbHandlerService.sendMsgToKAFKA("alarm_notification","Manual DatabaseBackup Completed Successfully on"+dt);
                logger.info("{} : Published successfully to Kafka topic: alarm_notification ",message);
            }

        } catch (Exception e) {
            logger.error("Failed to schedule backup based on Kafka message", e);
            dbHandlerService.sendMsgToKAFKA("alarm_notification","An error is occured while database backup is taken !!!");
        }
    }

    @KafkaListener(topics = Global.GET_DBBACKUP_LIST, groupId = "getdbbackuplist",containerFactory = "kafkaListenerContainerFactory")
    public void listenForDbBackupList(String message) {
        Date dt=new Date();
        logger.info("Received Message Of Db backup list "+":  " + message);
        try {
            dbHandlerService.sendBackupDirectoryContentToKafka(backupDir);
            dbHandlerService.sendMsgToKAFKA("alarm_notification","DatabaseBackup list sent");
            logger.info("{} : Published successfully to Kafka topic: alarm_notification ",message);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @KafkaListener(topics = Global.GET_DB_IMPORT, groupId = "getdbbackuplist",containerFactory = "kafkaListenerContainerFactory")
    public void listenForDbImport(String message) {
        Date dt=new Date();
        logger.info("Received Message Of Db backup list "+":  " + message);
        try {
            ImportingMessage importingMessage=objectMapper.readValue(message, ImportingMessage.class);
            dbHandlerService.dbImport(importingMessage);
            dbHandlerService.sendMsgToKAFKA("alarm_notification","Database Imported");
            logger.info("{} : Published successfully to Kafka topic: alarm_notification ",message);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}

