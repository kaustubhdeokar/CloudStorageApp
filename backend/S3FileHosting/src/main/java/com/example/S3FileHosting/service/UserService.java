package com.example.S3FileHosting.service;


import com.example.S3FileHosting.dto.UserDto;
import com.example.S3FileHosting.entity.Role;
import com.example.S3FileHosting.entity.User;
import com.example.S3FileHosting.exception.CustomerAlreadyExistsException;
import com.example.S3FileHosting.repo.RoleRepo;
import com.example.S3FileHosting.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void saveUser(UserDto userDto) throws CustomerAlreadyExistsException {

        if (checkIfUserEmailIsAlreadyRegistered(userDto.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer already exists in the database.");
        }

        String fullName = userDto.getFirstName() + " " + userDto.getLastName();
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        User user = new User(fullName,
                userDto.getEmail(),
                userDto.getPassword(),
                List.of(role));
        userRepository.save(user);
    }

    public boolean checkIfUserEmailIsAlreadyRegistered(String email) {
        User userByEmail = findUserByEmail(email);
        return userByEmail != null && userByEmail.getEmail() != null && !userByEmail.getEmail().isEmpty();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    public boolean checkCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;


    }
}
