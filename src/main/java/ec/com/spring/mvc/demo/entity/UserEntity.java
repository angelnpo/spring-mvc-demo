package ec.com.spring.mvc.demo.entity;

import ec.com.spring.mvc.demo.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User entity class.
 *
 * @author Angel Cuenca
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "email" }) })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String username;
	private String firstName;
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String country;
	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role.name()));
	}

	// @Override
	// public String getUsername() {
	// return this.email;
	// }
}
