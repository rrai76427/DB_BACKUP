/*
package com.crl.nms.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.crl.nms.common.utilities.Global;

import com.crl.nms.service.DbHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import java.util.HashMap;
import java.util.Map;

public class ConsumerActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerActor.class);

    ActorSystem actorSystem;

    ObjectMapper jsonobj=new ObjectMapper();

    DbHandlerService dbHandlerService;

    public static Props props(ActorSystem actorSystem,DbHandlerService dbHandlerService) {

        return Props.create(ConsumerActor.class,
                () -> new ConsumerActor(actorSystem,dbHandlerService));
    }

    private ConsumerActor(ActorSystem actorSystem,DbHandlerService dbHandlerService) {

        this.actorSystem=actorSystem;
        this.dbHandlerService=dbHandlerService;
        createConsumer();
    }
    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .build();
    }

    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.16:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "topic-response");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

*/
/*    private void createConsumer() {

        logger.info(" consumer started !!!");
        ContainerProperties containerProps = new ContainerProperties(Global.ADD_DEVICE);
        ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(
                consumerFactory(), containerProps);
        container.setupMessageListener((AcknowledgingMessageListener<String, String>) (record, acknowledgment) -> {
                try {
                    AddDeviceModel adddevice = jsonobj.readValue(record.value(), AddDeviceModel.class);
                    logger.info("Received message In Health Module: " + record.value());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
        });
        container.getContainerProperties().setPollTimeout(3000);
        container.start();
    }*//*

}
*/
