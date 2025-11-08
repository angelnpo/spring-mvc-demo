package ec.com.spring.mvc.demo;

import ec.com.spring.mvc.demo.entity.UserEntity;
import ec.com.spring.mvc.demo.enums.Role;
import ec.com.spring.mvc.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * App configuration.
 *
 * @author Angel Cuenca
 */
@SpringBootApplication
@Slf4j
public class SpringMvcDemoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Command line runner...");
        UserEntity user;
        Optional<UserEntity> userEntityOptional = this.userRepository.findByEmail("admin@mail.com");
        if (userEntityOptional.isEmpty()) {
            user = UserEntity.builder().username("admin").email("admin@mail.com").firstName("Joe")
                .lastName("Doe").password(this.passwordEncoder.encode("123456")).country("Ecuador")
                .role(Role.ADMIN).build();
            this.userRepository.save(user);
        }

        userEntityOptional = this.userRepository.findByEmail("guest@mail.com");
        if (userEntityOptional.isEmpty()) {
            user = UserEntity.builder().username("guest").email("guest@mail.com").firstName("Liam")
                .lastName("Kettle").password(this.passwordEncoder.encode("123456"))
                .country("Ecuador").role(Role.GUEST).build();
            this.userRepository.save(user);
        }
    }
}
