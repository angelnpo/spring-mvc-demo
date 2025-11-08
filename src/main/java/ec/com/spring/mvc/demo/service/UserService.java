package ec.com.spring.mvc.demo.service;

import ec.com.spring.mvc.demo.dto.UserDTO;
import ec.com.spring.mvc.demo.entity.UserEntity;
import ec.com.spring.mvc.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * User service.
 *
 * @author Angel Cuenca
 */
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user.
     *
     * @param userDTO
     * @return
     */
    public UserEntity register(UserDTO userDTO) {

        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO is null");
        }

        Optional<UserEntity> userEntityOptional =
            this.userRepository.findByEmail(userDTO.getEmail());

        if (userEntityOptional.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity =
            UserEntity.builder().username(userDTO.getUsername()).firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName()).email(userDTO.getEmail())
                .country(userDTO.getCountry())
                .password(this.passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole()).build();

        return this.userRepository.save(userEntity);
    }
}
