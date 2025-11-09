package com.vicsergeev.SpringHW.config;

import com.vicsergeev.SpringHW.dto.UserEventDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor 16.10.2025
 */

@Configuration
public class KafkaConfig {
    
    @Value("${spring.kafka.bootstrap-servers:kafka:29092}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, UserEventDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // use the bootstrap servers from the environment variable or default to kafka:29092
        String servers = System.getenv("SPRING_KAFKA_BOOTSTRAPSERVERS");
        if (servers == null || servers.isEmpty()) {
            servers = bootstrapServers;
        }
        System.out.println("Using Kafka bootstrap servers: " + servers);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, UserEventDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}