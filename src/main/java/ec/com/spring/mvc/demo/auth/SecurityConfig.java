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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * Security configuration class.
 *
 * @author Angel Cuenca
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final UserRepository userRepository;

    /**
     * @Override
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        AuthenticationManager authManager) throws Exception {

        SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
        EmailPasswordAuthFilter customFilter =
            new EmailPasswordAuthFilter(authManager, securityContextRepository);
        customFilter.setAuthenticationSuccessHandler(successHandler);
        customFilter.setAuthenticationFailureHandler(failureHandler);

        // @formatter:off
        return http
            .csrf(csrf -> csrf.disable())
            .securityContext(context ->
                    context.securityContextRepository(securityContextRepository))
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authRequest ->
                    authRequest.requestMatchers("/css/**", "/js/**", "/webjars/**", "/img/**",
                    "/login", "/register", "/error/**").permitAll()
                            .anyRequest().authenticated()
            )
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .logout(logout ->
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
            )
            .exceptionHandling(ex ->
                    ex.authenticationEntryPoint(
                            (req, res, authEx) ->
                                    res.sendRedirect("/spring-mvc-demo/login")
                            )//.accessDeniedPage("/error/403") //when enabled this; create endpoint /error/403. Or customize and use .accessDeniedHandler()
            )
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
