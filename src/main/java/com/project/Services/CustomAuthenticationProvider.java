package com.project.Services;

import com.project.Models.User;
import com.project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        User user = userRepository.findByUserLogin(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);

        if (user == null) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika!");
        } else if(!encoder.matches(rawPassword, user.getUserPassword())) {
            throw new BadCredentialsException("Błedne hasło!");
        } else if (!user.isAccountNonLocked()){
            throw new LockedException("Konto jest zablokowane!");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole()));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, rawPassword,
                grantedAuthorities);

        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
