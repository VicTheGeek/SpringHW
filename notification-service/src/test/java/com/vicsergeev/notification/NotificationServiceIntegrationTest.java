package com.vicsergeev.notification;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.vicsergeev.notification.dto.UserEventDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

import jakarta.mail.internet.MimeMessage;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"user-events"})
class NotificationServiceIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private KafkaTemplate<String, UserEventDTO> kafkaTemplate;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, UserEventDTO> pf = new DefaultKafkaProducerFactory<>(producerProps);
        kafkaTemplate = new KafkaTemplate<>(pf);

        ServerSetup setup = new ServerSetup(3025, null, "smtp");
        greenMail = new GreenMail(setup);
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        if (greenMail != null) {
            greenMail.stop();
        }
    }

    @Test
    void shouldSendEmailOnCreateEvent() throws Exception {
        String email = "test@example.com";
        kafkaTemplate.send("user-events", new UserEventDTO(UserEventDTO.CREATE, email, "Test"));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSizeGreaterThanOrEqualTo(1);
        String content = (String) messages[0].getContent();
        assertThat(content).contains("Ваш аккаунт на сайте ваш сайт был успешно создан");
    }

    @Test
    void shouldSendEmailOnDeleteEvent() throws Exception {
        String email = "test2@example.com";
        kafkaTemplate.send("user-events", new UserEventDTO(UserEventDTO.DELETE, email, "Test2"));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSizeGreaterThanOrEqualTo(1);
        String content = (String) messages[0].getContent();
        assertThat(content).contains("Ваш аккаунт был удалён");
    }
}


