package thuha.com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import thuha.com.jwt.model.Account;
import thuha.com.jwt.model.LoginRequest;
import thuha.com.jwt.repo.AccountRepo;
import thuha.com.jwt.security.CustomUserDetails;
import thuha.com.jwt.security.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountRepo accountRepo;

    @GetMapping
    public String auth() {
        return "Hello World";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateToken(auth);
        System.out.println(token);
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(token);
    }
    @GetMapping("/login-with-google")
    public RedirectView googleLogin() {
        // chuyển hướng sang login bằng gg
        return new RedirectView("/oauth2/authorization/google");
    }

}
