package tech.linqu.spring.cloud.starter.auth.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.linqu.spring.cloud.starter.auth.domain.User;
import tech.linqu.spring.cloud.starter.auth.repository.UserRepository;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.auth.tests.data.DataUser;
import tech.linqu.spring.cloud.starter.tests.tests.SpringComponentTests;

class MysqlUserDetailsServiceTest extends SpringComponentTests {

    @MockBean
    private UserRepository userRepository;

    private MysqlUserDetailsService service;

    @BeforeAll
    void beforeAll() {
        this.service = new MysqlUserDetailsService(userRepository);
    }

    @Test
    void shouldLoadUserByUsernameSuccess() {
        UserRecord record = new DataUser.User1().get();
        User user = new User();
        user.setId(record.getId());
        user.setUsername(record.getUsername());
        Optional<User> optional = Optional.of(user);
        doReturn(optional).when(userRepository).findByUsername(any());
        UserDetails userDetails = service.loadUserByUsername(record.getUsername());
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    void givenUserNotExists_whenLoadUser_shouldThrowError() {
        Optional<User> optional = Optional.empty();
        doReturn(optional).when(userRepository).findByUsername(any());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(""));
    }
}
