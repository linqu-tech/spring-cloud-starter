package tech.linqu.spring.cloud.starter.auth.config.jose;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class JwksTest {

    @Test
    void shouldGenerateRsaSuccess() {
        assertNotNull(Jwks.generateRsa());
    }

    @Test
    void shouldGenerateEcSuccess() {
        assertNotNull(Jwks.generateEc());
    }

    @Test
    void shouldGenerateSecretSuccess() {
        assertNotNull(Jwks.generateSecret());
    }
}
