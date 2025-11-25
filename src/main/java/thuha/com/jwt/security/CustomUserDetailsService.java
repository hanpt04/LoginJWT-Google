package thuha.com.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thuha.com.jwt.model.Account;
import thuha.com.jwt.repo.AccountRepo;
import thuha.com.jwt.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountService.getAccountByPhone(username);
        if (acc == null || !acc.isActive()) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_"+acc.getRole())
        );
        return new CustomUserDetails(
                acc.getId(),
                acc.getName(),
                acc.getPhone(),
                acc.getPassword(),
                authorities,
                acc.isActive()
        );
    }

    public UserDetails loadById(int id){
        Account acc = accountRepo.getById(id);
        if (acc == null || !acc.isActive()) {
            throw new UsernameNotFoundException("Not Found");
        }
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_"+acc.getRole())
        );
        return new CustomUserDetails(
                acc.getId(),
                authorities
        );
    }
}
