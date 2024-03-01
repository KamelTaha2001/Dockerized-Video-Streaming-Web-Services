package kamel.video.streaming.videostreaming.controller;

import jakarta.servlet.http.HttpSession;
import kamel.video.streaming.videostreaming.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login(HttpSession session) {
        session.removeAttribute("authentication");
        return "login";
    }
}
