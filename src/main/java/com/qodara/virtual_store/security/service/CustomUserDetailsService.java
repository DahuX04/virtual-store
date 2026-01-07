package com.qodara.virtual_store.security.service;


import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //busca al usuario por su username o email
        var account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró al usuario con el email: " + email));

        if (Objects.equals(account.getRole().getName(), "ROLE_DISABLE")) {
            return User.withUsername(account.getEmail())
                    .password(account.getPassword())
                    .roles("DISABLE")
                    .build();
        }

        if (Objects.equals(account.getRole().getName(), "ROLE_ADMIN")) {
            return User.withUsername(account.getEmail())
                    .password(account.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        return User.withUsername(account.getEmail())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}
