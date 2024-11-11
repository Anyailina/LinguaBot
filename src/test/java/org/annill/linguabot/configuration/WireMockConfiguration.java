package org.annill.linguabot.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfiguration {

    @Bean
    public WireMockServer getWireMock() {
        WireMockServer wireMockServer = new WireMockServer(8081);
        WireMock.configureFor("localhost", 8081);
        return wireMockServer;
    }

}
