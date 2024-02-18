package com.example.S3FileHosting.service;

import com.example.S3FileHosting.entity.Role;
import com.example.S3FileHosting.entity.User;
import com.example.S3FileHosting.exception.CustomerAlreadyExistsException;
import com.example.S3FileHosting.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User currentUser = null;
        Optional<User> user = userRepository.findByUsername(usernameOrEmail);
        if (!user.isPresent()) {
            user = userRepository.findByEmail(usernameOrEmail);
            if (!user.isPresent()) {
                throw new CustomerAlreadyExistsException(HttpStatus.BAD_REQUEST,
                        String.format("User not found with email/username %s", usernameOrEmail));
            }
        }
        currentUser = user.get();


        Set<GrantedAuthority> authorities = currentUser.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(usernameOrEmail,
                currentUser.getPassword(), authorities);

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }

}
