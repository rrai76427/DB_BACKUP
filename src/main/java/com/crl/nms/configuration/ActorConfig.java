package com.crl.nms.configuration;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActorConfig {
    @Bean
    public ActorSystem actorSystem() {

        ActorSystem system = ActorSystem.create("NeHelthPollEngine");
        return system;
    }
}
