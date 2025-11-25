package thuha.com.jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import thuha.com.jwt.model.Account;
import thuha.com.jwt.model.Role;
import thuha.com.jwt.service.AccountService;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {
    private final AccountService accountService;

    public JwtApplication(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        Account acc = new Account();
        acc.setActive(true);
        acc.setPassword("123");
        acc.setName("John");
        acc.setPhone("0987654321");
        acc.setRole(Role.USER);
        accountService.save(acc);

        Account acc2 = new Account();
        acc2.setActive(true);
        acc2.setPassword("123");
        acc2.setName("John");
        acc2.setPhone("0123456789");
        acc2.setRole(Role.ADMIN);
        acc2.setMail("nha3697@gmail.com");
        accountService.save(acc2);

        System.out.println(accountService.getAccountByPhone(acc.getPhone()));
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

}
