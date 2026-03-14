package ec.com.spring.mvc.demo.dto;

import ec.com.spring.mvc.demo.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data transfer object.
 *
 * @author Angel Cuenca
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @NotEmpty(message = "Please enter your username")
    @Size(min = 5, max = 16, message = "Username must be between 8 and 16 characters")
    private String username;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Please enter your email")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotEmpty(message = "Please enter your password")
    private String password;
    private String country;
    @NotNull(message = "Please select a role")
    private Role role;
}
