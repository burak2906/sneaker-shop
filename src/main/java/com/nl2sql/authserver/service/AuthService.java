package com.nl2sql.authserver.service;

import com.nl2sql.authserver.config.security.JwtGenerator;
import com.nl2sql.authserver.entity.Role;
import com.nl2sql.authserver.entity.User;
import com.nl2sql.authserver.exception.BadRequestException;
import com.nl2sql.authserver.repository.RoleRepository;
import com.nl2sql.authserver.repository.UserRepository;
import com.nl2sql.authserver.service.dto.LoginDto;
import com.nl2sql.authserver.service.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;


    public void register(RegisterDto registerDto) {

        if(userRepository.existsUserByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Email is already used");
        }

        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findRoleByName("USER").get());

        userRepository.save(new User()
                .setEmail(registerDto.getEmail())
                .setPassword(passwordEncoder.encode(registerDto.getPassword()))
                .setUsername(registerDto.getUsername())
                .setRoles(roleList));
    }

    public String login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findUserByEmail(loginDto.getEmail());
        if(optionalUser.isEmpty()) {
            throw new BadRequestException("Wrong credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);

    }
}
