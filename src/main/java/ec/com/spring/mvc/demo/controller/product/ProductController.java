package ec.com.spring.mvc.demo.controller.product;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Product controller.
 *
 * @author Angel Cuenca
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping({"/list"})
    @PreAuthorize("hasAnyAuthority('ADMIN','USER', 'GUEST')")
    public String findAll() {
        return "/product/product";
    }

    @GetMapping({"/register"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String register() {
        return "/product/register";
    }
}
