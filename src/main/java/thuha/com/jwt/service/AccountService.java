package thuha.com.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thuha.com.jwt.model.Account;
import thuha.com.jwt.repo.AccountRepo;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    public Account getAccountByEmail(String email) {
        return accountRepo.getAccountByMail(email);
    }

    public Account getAccountByPhone(String phone) {
        return accountRepo.findByPhone(phone);
    }

    public Account save (Account account) {
        return accountRepo.save(account);
    }
}
