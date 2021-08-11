package tech.linqu.spring.cloud.starter.gateway.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.gateway.config.GatewayMetricsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.SocketUtils;
import tech.linqu.spring.cloud.starter.gateway.GatewayApplication;

@SpringBootTest(
    classes = { ReplaceLocationResponseHeaderGatewayFilterFactoryTest.TestConfig.class },
    properties = "management.server.port=${test.port}",
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class ReplaceLocationResponseHeaderGatewayFilterFactoryTest {

    protected static int managementPort;

    @Autowired
    GatewayMetricsProperties metricsProperties;

    @LocalServerPort
    protected int port = 0;

    protected WebTestClient webClient;

    protected String baseUri;

    @BeforeAll
    public static void beforeClass() {
        managementPort = SocketUtils.findAvailableTcpPort();
        System.setProperty("test.port", String.valueOf(managementPort));
    }

    @AfterAll
    public static void afterClass() {
        System.clearProperty("test.port");
    }

    @BeforeEach
    public void setup() {
        baseUri = "http://localhost:" + port;
        this.webClient = WebTestClient.bindToServer()
            .responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
    }

    @Test
    void givenConfig_ShouldToStringSuccess() {
        ReplaceLocationResponseHeaderGatewayFilterFactory.Config config =
            new ReplaceLocationResponseHeaderGatewayFilterFactory.Config();
        config.setLocationHeaderName("Location");
        config.setMatchRegex("Origin");
        config.setReplacement("Replace");
        ReplaceLocationResponseHeaderGatewayFilterFactory factory =
            new ReplaceLocationResponseHeaderGatewayFilterFactory();
        assertEquals(
            "[ReplaceLocationResponseHeader locationHeaderName = 'Location', "
                + "matchRegex = 'Origin', replacement = 'Replace']",
            factory.apply(config).toString());
    }

    @Test
    void whenWithoutLocation_ShouldNotReplaceLocation() {
        webClient.get().uri("/test/api/no/location").exchange()
            .expectStatus().isOk()
            .expectHeader().doesNotExist("Location");
    }

    @Test
    void whenRequestDirect_ShouldNotReplaceLocation() {
        webClient.get().uri("/test/api/preserve").exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Location", "/preserved/test");
    }

    @Test
    void whenRequestOthers_ShouldReplaceLocation() {
        webClient.get().uri("/test/api/replace").exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Location", "/replacement/test");
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    @Import(GatewayApplication.class)
    @LoadBalancerClient(name = "test", configuration = LoadBalancerConfig.class)
    protected static class TestConfig {

    }

    protected static class LoadBalancerConfig {

        @LocalServerPort
        int port;

        @Bean
        public ServiceInstanceListSupplier fixedServiceInstanceListSupplier() {
            return ServiceInstanceListSuppliers.from("test",
                new DefaultServiceInstance("test", "test", "localhost", port, false)
            );
        }
    }
}
