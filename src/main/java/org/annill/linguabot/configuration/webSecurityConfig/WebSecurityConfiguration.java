package org.annill.linguabot.configuration.webSecurityConfig;

import feign.Client;
import feign.Contract;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    @Bean
    public Client httpClient() {
        return new Client.Default(sslContext().getSocketFactory(), null);
    }

    @SneakyThrows
    public SSLContext sslContext() {
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

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);

        return sslContext;
    }
}
