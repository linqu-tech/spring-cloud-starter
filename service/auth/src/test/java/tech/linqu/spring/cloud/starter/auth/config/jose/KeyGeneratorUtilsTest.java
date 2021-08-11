package tech.linqu.spring.cloud.starter.auth.config.jose;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.security.KeyPairGenerator;
import javax.crypto.KeyGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class KeyGeneratorUtilsTest {

    @Test
    void shouldGenerateSecretKeySuccess() {
        assertNotNull(KeyGeneratorUtils.generateSecretKey());
    }

    @Test
    void shouldGenerateSecretKeyThrowError() {
        try (MockedStatic<KeyGenerator> generator = mockStatic(KeyGenerator.class)) {
            generator.when(() -> KeyGenerator.getInstance(any())).thenThrow(new RuntimeException());
            assertThrows(IllegalStateException.class, KeyGeneratorUtils::generateSecretKey);
        }
    }

    @Test
    void shouldGenerateRsaKeySuccess() {
        assertNotNull(KeyGeneratorUtils.generateRsaKey());
    }

    @Test
    void shouldGenerateRsaKeyThrowError() {
        try (MockedStatic<KeyPairGenerator> generator = mockStatic(KeyPairGenerator.class)) {
            generator.when(() -> KeyPairGenerator.getInstance(any()))
                .thenThrow(new RuntimeException());
            assertThrows(IllegalStateException.class, KeyGeneratorUtils::generateRsaKey);
        }
    }

    @Test
    void shouldGenerateEcKeySuccess() {
        assertNotNull(KeyGeneratorUtils.generateEcKey());
    }

    @Test
    void shouldGenerateEcKeyThrowError() {
        try (MockedStatic<KeyPairGenerator> generator = mockStatic(KeyPairGenerator.class)) {
            generator.when(() -> KeyPairGenerator.getInstance(any()))
                .thenThrow(new RuntimeException());
            assertThrows(IllegalStateException.class, KeyGeneratorUtils::generateEcKey);
        }
    }
}
