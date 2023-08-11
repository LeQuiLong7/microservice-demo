package com.lql.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("orders", 1, (short) 1);
    }
}
