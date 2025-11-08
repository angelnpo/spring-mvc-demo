package ec.com.spring.mvc.demo.service;

import ec.com.spring.mvc.demo.dto.UserDTO;
import ec.com.spring.mvc.demo.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * UserService test.
 *
 * @author Angel Cuenca
 */
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * Test register null userDTO.
     */
    @Test
    void testRegister_NullUserDTO_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> this.userService.register(null));
    }

    /**
     * Test register user already exists.
     */
    @Test
    void testRegister_UserAlreadyExists_ThrowsRuntimeException() {
        UserDTO userDTO1 =
            UserDTO.builder().username("testuser").email("test@example.com").password("password123")
                .passwordConfirm("password123").role(Role.USER).build();

        userService.register(userDTO1);

        UserDTO userDTO2 = UserDTO.builder().username("testuser2").email("test@example.com")
            .password("password123").passwordConfirm("password123").role(Role.USER).build();

        assertThrows(RuntimeException.class, () -> this.userService.register(userDTO2));
    }

    /**
     * Test register successful registration.
     */
    @Test
    void testRegister_SuccessfulRegistration_ReturnsUserEntity() {
        UserDTO userDTO = UserDTO.builder().username("testuser").firstName("Test").lastName("User")
            .email("test@example.com").password("password123").passwordConfirm("password123")
            .country("Ecuador").role(Role.USER).build();

        var result = this.userService.register(userDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
        assertNotNull(result.getPassword()); // encoded
    }
}
