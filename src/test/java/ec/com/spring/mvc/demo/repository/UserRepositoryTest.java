package ec.com.spring.mvc.demo.repository;

import ec.com.spring.mvc.demo.TestConfig;
import ec.com.spring.mvc.demo.entity.UserEntity;
import ec.com.spring.mvc.demo.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * UserRepository test.
 *
 * @author Angel Cuenca
 */
@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Test save and find by id.
	 */
	@Test
	void testSaveAndFindById() {
		UserEntity user = UserEntity.builder().username("testuser").email("test@example.com")
				.password("encodedpassword").role(Role.USER).build();

		UserEntity saved = this.userRepository.save(user);
		assertNotNull(saved.getId());

		Optional<UserEntity> found = this.userRepository.findById(saved.getId());
		assertTrue(found.isPresent());
		assertEquals("testuser", found.get().getUsername());
	}

	/**
	 * Test find by username.
	 */
	@Test
	void testFindByUsername() {
		UserEntity user = UserEntity.builder().username("testuser").email("test@example.com")
				.password("encodedpassword").role(Role.USER).build();

		this.userRepository.save(user);

		Optional<UserEntity> found = this.userRepository.findByUsername("testuser");
		assertTrue(found.isPresent());
		assertEquals("testuser", found.get().getUsername());
	}

	/**
	 * Test find by email.
	 */
	@Test
	void testFindByEmail() {
		UserEntity user = UserEntity.builder().username("testuser").email("test@example.com")
				.password("encodedpassword").role(Role.USER).build();

		this.userRepository.save(user);

		Optional<UserEntity> found = this.userRepository.findByEmail("test@example.com");
		assertTrue(found.isPresent());
		assertEquals("test@example.com", found.get().getEmail());
	}

	/**
	 * Test find by username not found.
	 */
	@Test
	void testFindByUsername_NotFound() {
		Optional<UserEntity> found = this.userRepository.findByUsername("nonexistent");
		assertFalse(found.isPresent());
	}

	/**
	 * Test find by email not found.
	 */
	@Test
	void testFindByEmail_NotFound() {
		Optional<UserEntity> found = this.userRepository.findByEmail("nonexistent@example.com");
		assertFalse(found.isPresent());
	}
}
