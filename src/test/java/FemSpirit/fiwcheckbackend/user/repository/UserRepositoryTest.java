package FemSpirit.fiwcheckbackend.user.repository;

import FemSpirit.fiwcheckbackend.role.entity.Role;
import FemSpirit.fiwcheckbackend.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.hibernate.exception.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    Role testRole;
    User testUser;

    @BeforeEach
    void setUp() {
        testRole = new Role("STUDENT");
        entityManager.persistAndFlush(testRole);

        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole(testRole);
    }

    // Test 1: User anhand der E-Mail finden
    @Test
    void shouldFindUserByEmail() {

        // Arrange
        entityManager.persistAndFlush(testUser);

        // Act
        var result = userRepository.findByEmail("test@example.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@example.com");
    }

    // Test 2: E-Mail muss eindeutig sein
    @Test
    void shouldNotAllowDuplicateEmail() {

        // Arrange
        entityManager.persistAndFlush(testUser);

        User secondUser = new User();
        secondUser.setUsername("seconduser");
        secondUser.setEmail("test@example.com");
        secondUser.setPassword("password456");

        // Act & Assert
        assertThatThrownBy(() -> {
            entityManager.persistAndFlush(secondUser);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    //CRUD Methoden Tests
    // Test 3: User speichern
    @Test
    void shouldSaveUser() {

        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    // Test 4: User anhand der ID finden
    @Test
    void shouldFindUserById() {

        // Arrange
        User savedUser = entityManager.persistAndFlush(testUser);

        // Act
        var result = userRepository.findById(savedUser.getUserId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@example.com");
    }

    // Test 5: User ändern
    @Test
    void shouldUpdateUser() {

        // Arrange
        User savedUser = entityManager.persistAndFlush(testUser);

        // Act
        savedUser.setUsername("updateduser");
        userRepository.saveAndFlush(savedUser);

        // Assert
        assertThat(userRepository.findById(savedUser.getUserId()))
                .isPresent()
                .get()
                .extracting(User::getUsername)
                .isEqualTo("updateduser");
    }

    // Test 6: User löschen
    @Test
    void shouldDeleteUser() {

        // Arrange
        User savedUser = entityManager.persistAndFlush(testUser);
        Long userId = savedUser.getUserId();

        // Act
        userRepository.deleteById(userId);

        // Assert
        assertThat(userRepository.findById(userId)).isEmpty();
    }
}
