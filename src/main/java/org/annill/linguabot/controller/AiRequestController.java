package org.annill.linguabot.controller;

import kong.unirest.core.Unirest;
import org.annill.linguabot.configuration.ai.AiConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@RestController
public class AiRequestController {
    private final AiConfiguration aiConfiguration;

    public AiRequestController(AiConfiguration aiConfiguration) {
        this.aiConfiguration = aiConfiguration;
    }


    @GetMapping("/cool")
    public void cool() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, InterruptedException {
        String token = aiConfiguration.getAccessToken();
        SSLContext sslContext = aiConfiguration.getSslContext();

        Unirest.config()
                .verifySsl(true)
                .sslContext(sslContext);


        kong.unirest.core.HttpResponse
                <String> httpResponse = Unirest.post("https://gigachat.devices.sberbank.ru/api/v1/chat/completions")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n  \"model\": \"GigaChat\",\n  \"messages\": [\n    {\n      \"role\": \"user\",\n      \"content\": \"Отправь в формате JSON перевод слова кошка\"\n    }\n  ],\n  \"stream\": false,\n  \"update_interval\": 0\n}")
                .asString();
        System.out.println(httpResponse.getBody());
    }

}
