package com.vicsergeev.notification;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
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
    void shouldSendEmailViaApi() throws Exception {
        String email = "api@example.com";
        mockMvc.perform(post("/notifications/created").param("email", email))
                .andExpect(status().isNoContent());

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSizeGreaterThanOrEqualTo(1);
        String content = (String) messages[0].getContent();
        assertThat(content).contains("Ваш аккаунт на сайте ваш сайт был успешно создан");
    }
}


