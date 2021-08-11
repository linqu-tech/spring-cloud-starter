package tech.linqu.spring.cloud.starter.config.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(SecurityConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SecurityConfigurationTest {

    @Autowired
    private TestRestTemplate template;

    @Value("${spring.cloud.config.server.prefix}")
    private String configPrefix;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Test
    public void whenGetActuatorHealth_shouldSucceedWith200() {
        ResponseEntity<String> result = template
            .getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenAuthFailed_whenGetConfig_shouldResponseWith403() {
        ResponseEntity<String> result = template
            .withBasicAuth("fake-username", "fake-password")
            .getForEntity("/" + configPrefix + "/registry/dev", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void givenNotAuthed_whenGetConfig_shouldResponseWith401() {
        ResponseEntity<String> result = template
            .getForEntity("/" + configPrefix + "/registry/dev", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void givenBasicAuth_whenGetConfig_shouldSucceedWith200() {
        ResponseEntity<String> result = template
            .withBasicAuth(username, password)
            .getForEntity("/" + configPrefix + "/registry/dev", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
