package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByUsername() {
        User user = User.builder()
                .username("johndoe")
                .email("john@example.com")
                .password("encodedpassword")
                .fullName("John Doe")
                .enabled(true)
                .build();

        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("johndoe");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldCheckUsernameExists() {
        User user = User.builder()
                .username("janedoe")
                .email("jane@example.com")
                .password("encodedpassword")
                .enabled(true)
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByUsername("janedoe")).isTrue();
        assertThat(userRepository.existsByUsername("nobody")).isFalse();
    }
}
