package kamel.video.streaming.videostreaming.service;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LoginService {

    private final RestTemplate restTemplate;
    private final Environment environment;

    public LoginService(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public boolean authenticate(String email, String password) {
        String authDomain = environment.getProperty("AUTH_SERVICE_DOMAIN");
        String authPort = environment.getProperty("AUTH_SERVICE_PORT");
        HttpHeaders headers = new HttpHeaders();
        headers.set("email", email);
        headers.set("password", password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                "http://{domain}:{port}/auth", HttpMethod.GET, entity, Boolean.class, authDomain, authPort
        );
        if (response.hasBody())
            return response.getBody();
        else
            return false;
    }
}
