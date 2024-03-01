package kamel.video.streaming.videostreaming.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kamel.video.streaming.videostreaming.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/login")
public class LoginRestController {
    private final LoginService service;

    public LoginRestController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public RedirectView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (service.authenticate(email, password)) {
            session.setAttribute("authentication", "true");
            return new RedirectView("/videos");
        } else  {
            return new RedirectView("/login");
        }
    }
}
