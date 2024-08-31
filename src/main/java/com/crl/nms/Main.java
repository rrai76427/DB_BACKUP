<<<<<<< HEAD
package com.crl.nms;

import com.crl.nms.service.DbHandlerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        // Get the path where the JAR file is executed
        String jarExecutionPath = System.getProperty("user.dir");
        System.out.println(jarExecutionPath);

        // Construct the path to applicationnedetail.properties relative to the execution path
        String dynamicPath = "file:" + jarExecutionPath + "/applicationnedetail.properties";

        // Set the dynamic path for spring.config.location
        System.setProperty("spring.config.location", dynamicPath);
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(DbHandlerService dbHandlerService) {

        return args -> {
            dbHandlerService.initiateActor(dbHandlerService);
        };
    }
}
=======
package com.crl.nms;

import com.crl.nms.common.utilities.Global;
import com.crl.nms.service.DbHandlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaJSONStringMsgSender;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        // Get the path where the JAR file is executed
        String jarExecutionPath = System.getProperty("user.dir");
        System.out.println(jarExecutionPath);

        // Construct the path to application.properties relative to the execution path
        String dynamicPath = "file:" + jarExecutionPath + "/applicationdbbackup.properties";

        System.out.println("Applicationdbbackup.properties should be at " + dynamicPath);

        // Set the dynamic path for spring.config.location
        System.setProperty("spring.config.location", dynamicPath);

        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(DbHandlerService dbHandlerService) {
        return args -> {
            // Using instance method instead of static method
          //  sendMsgToNeAlarm("create_backup");
        };
    }

    private void sendMsgToNeAlarm(Object messages) {
        try {
            String message = objectMapper.writeValueAsString(messages);
            kafkaJSONStringMsgSender.send(Global.CREATE_BACKP, message);
            kafkaJSONStringMsgSender.send(Global.GET_DBBACKUP_LIST, "backup_list");
        } catch (Exception e) {
            logger.error("Failed to send message: ", e);
        }
    }
}
>>>>>>> ce7b643 (CBTC_DB_BACKUP)
