package ec.com.spring.mvc.demo.controller.client;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Client controller.
 *
 * @author Angel Cuenca
 */
@Controller
@RequestMapping("/client")
@Secured({"ADMIN"})
public class ClientController {

    @GetMapping({"/list"})
    public String findAll() {
        return "/client/client";
    }

    @GetMapping({"/register"})
    public String register() {
        return "/client/client";
    }
}
