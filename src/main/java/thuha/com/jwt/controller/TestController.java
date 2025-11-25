package thuha.com.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import thuha.com.jwt.model.Account;
import thuha.com.jwt.repo.AccountRepo;
import thuha.com.jwt.security.CustomUserDetails;
import thuha.com.jwt.service.AccountService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private final AccountService accountService;
    private final AccountRepo accountRepo;

    public TestController(AccountService accountService, AccountRepo accountRepo) {
        this.accountService = accountService;
        this.accountRepo = accountRepo;
    }

    @GetMapping
    public String test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Auth: " + auth);

        return "user";
    }

    @GetMapping("/admin")
    public String test2() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Auth: " + auth);
        System.out.println(accountRepo.findAll());
        return "admin";
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> getMe(){
        Map<String,Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        int id = userDetails.getUserId();
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        map.put("id", account.getId());
        map.put("name", account.getName());
        map.put("role", account.getRole());
        map.put("phone", account.getPhone());
        map.put("mail", account.getMail());
        return ResponseEntity.ok(map);
    }
}
