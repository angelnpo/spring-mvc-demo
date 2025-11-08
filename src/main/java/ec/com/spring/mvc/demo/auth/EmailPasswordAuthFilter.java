package ec.com.spring.mvc.demo.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * Email password authentication filter.
 *
 * @author Angel Cuenca
 */
public class EmailPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final SecurityContextRepository securityContextRepository;

    /**
     * Constructor.
     *
     * @param authManager
     * @param securityContextRepository
     */
    public EmailPasswordAuthFilter(AuthenticationManager authManager,
        SecurityContextRepository securityContextRepository) {

        this.setAuthenticationManager(authManager);
        this.securityContextRepository = securityContextRepository;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        String email = request.getParameter("email");
        String password = obtainPassword(request);

        email = (email != null) ? email.trim() : "";
        password = (password != null) ? password : "";

        UsernamePasswordAuthenticationToken authRequest =
            new UsernamePasswordAuthenticationToken(email, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        // Delegate to success handler
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {

        // Delegate to failure handler
        this.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
