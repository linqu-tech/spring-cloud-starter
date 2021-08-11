package tech.linqu.spring.cloud.starter.auth.domain;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void getId() {
        assertNull(new User().getId());
    }

    @Test
    void getUsername() {
        assertNull(new User().getUsername());
    }

    @Test
    void getPassword() {
        assertNull(new User().getPassword());
    }

    @Test
    void getAuthorities() {
        assertTrue(new User().getAuthorities().isEmpty());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(new User().isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(new User().isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(new User().isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(new User().isEnabled());
    }
}
