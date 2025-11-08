package ec.com.spring.mvc.demo.repository;

import ec.com.spring.mvc.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository.
 *
 * @author Angel Cuenca
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Find user by username.
     *
     * @param username
     * @return
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find user by email.
     *
     * @param email
     * @return
     */
    Optional<UserEntity> findByEmail(String email);
}
