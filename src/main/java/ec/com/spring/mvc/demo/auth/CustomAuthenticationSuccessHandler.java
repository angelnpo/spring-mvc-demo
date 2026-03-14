package ec.com.spring.mvc.demo.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom authentication success handler.
 *
 * @author Angel Cuenca
 */
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Constructor.
     */
    public CustomAuthenticationSuccessHandler() {
        this.setDefaultTargetUrl("/home");
        this.setAlwaysUseDefaultTargetUrl(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {

        log.info("✅ User " + authentication.getName() + " logged in successfully");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
