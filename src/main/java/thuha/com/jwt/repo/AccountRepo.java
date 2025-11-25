package thuha.com.jwt.repo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import thuha.com.jwt.model.Account;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account getAccountByMail(String email);
    Account findByPhone(String phone);

    Optional<Account> findByMail(String mail);
}
