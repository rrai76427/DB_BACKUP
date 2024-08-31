package com.crl.nms.kafka;

import com.crl.nms.common.utilities.Global;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

//@Configuration
//
//public class MessageListener {
//
//    @KafkaListener(topics = Global.ADD_DEVICE, groupId = "add_device",containerFactory = "kafkaListenerContainerFactory")
//    public void listenGroupFoo(String message) {
//        System.out.println("Received Message  Add Device: " + message);
//    }
//}
