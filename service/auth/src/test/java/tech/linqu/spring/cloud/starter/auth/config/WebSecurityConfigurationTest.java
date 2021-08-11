package tech.linqu.spring.cloud.starter.auth.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class WebSecurityConfigurationTest {

    private void shouldEncodeSuccess(String prefix) {
        MysqlUserDetailsService service = mock(MysqlUserDetailsService.class);
        WebSecurityConfiguration configuration = new WebSecurityConfiguration(service);
        PasswordEncoder encoder = configuration.passwordEncoder();
        String password = "password";
        String encoded = encoder.encode(prefix + password);
        assertTrue(encoder.matches(password, encoded));
    }

    @Test
    void shouldEncodePasswordSuccess() {
        shouldEncodeSuccess("");
    }

    @Test
    void shouldEncodeBcryptPasswordSuccess() {
        shouldEncodeSuccess("{bcrypt}");
    }

    @Test
    void shouldEncodePlainPasswordSuccess() {
        shouldEncodeSuccess("{plain}");
    }

    @Test
    void shouldEncodeOtherPasswordSuccess() {
        shouldEncodeSuccess("{other}");
    }
}
