package org.annill.linguabot.configuration.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


@Component
@Data
public class AiConfiguration {
    @Value("${ai.url}")
    private String url;
    @Value("${ai.payload}")
    private String payLoad;
    @Value("${ai.clientSecret}")
    private String clientSecret;
    @Value("${ai.authData}")
    private String authData;
    private SSLContext sslContext;

    public HttpClient getHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certificate;
        try (FileInputStream fis = new FileInputStream("src/main/resources/certificates/russiantrustedca.pem")) {
            certificate = (X509Certificate) cf.generateCertificate(fis);
        }

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);  // Пустой keystore
        keyStore.setCertificateEntry("cert", certificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);

        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
    }

    public String getAccessToken() throws IOException, InterruptedException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        HttpClient client = getHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .header("RqUID", clientSecret)
                .header("Authorization", "Basic " + authData)
                .POST(HttpRequest.BodyPublishers.ofString(payLoad))  // Добавьте body, если это необходимо
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode.get("access_token").asText();
    }
}
