package ec.com.spring.mvc.demo.auth;

import ec.com.spring.mvc.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class.
 *
 * @author Angel Cuenca
 */
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfigSimple {

    private final UserRepository userRepository;

    /**
     * @Override
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        AuthenticationManager authManager) throws Exception {

        // @formatter:off        
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/css/**", "/js/**", "/webjars/**", "/img/**",
                    "/login", "/register", "/error/**")
                .permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.loginPage("/login")
                .usernameParameter("email")//Change username parameter to email
                 //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true)                
                //.failureUrl("/login?error")
            )            
            .logout(logout -> logout.logoutUrl("/logout")
                //.logoutSuccessUrl("/login?logout")
            )
            .authenticationProvider(this.authenticationProvider())
            //.exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))
            .build();
        
        // @formatter:on
    }

    /**
     * Bean authenticationManager.
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Bean authenticationProvider.
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(this.userDetailsService());
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    /**
     * Bean passwordEncoder.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean userDetailsService.
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // return username -> this.userRepository.findByUsername(username)
        // .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return email -> this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
