package ec.com.spring.mvc.demo.controller;

import ec.com.spring.mvc.demo.dto.UserDTO;
import ec.com.spring.mvc.demo.enums.Role;
import ec.com.spring.mvc.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Login controller.
 *
 * @author Angel Cuenca
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Authentication authentication) {

        if (authentication != null) {
            return "redirect:/home";
        }

        return "/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", UserDTO.builder().role(Role.USER).build());
        return "register";
    }

    /**
     * Register a new user.
     *
     * @param userDTO
     * @param result
     * @param model
     * @param flash
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDTO userDTO, BindingResult result,
        Model model, RedirectAttributes flash) {

        if (result.hasErrors()) {
            model.addAttribute("userDTO", userDTO);
            return "register";
        }

        try {
            this.userService.register(userDTO);
            flash.addFlashAttribute("success", "User registered successfully");
            return "redirect:/register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
