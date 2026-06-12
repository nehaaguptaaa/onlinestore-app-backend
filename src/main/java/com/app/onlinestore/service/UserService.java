package com.app.onlinestore.service;

import com.app.onlinestore.dto.LoginDTO;
import com.app.onlinestore.dto.RegisterDTO;
import com.app.onlinestore.model.User;
import com.app.onlinestore.repository.UserRepository;
import com.app.onlinestore.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email));
        return new org.springframework.security.core
                .userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>());
    }

    public String register(RegisterDTO dto) {
        if (userRepository.findByEmail(
                dto.getEmail()).isPresent()) {
            return "Email already registered!";
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(
                passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginDTO dto) {
        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElse(null);
        if (user == null) return "User not found!";
        if (!passwordEncoder.matches(
                dto.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }
        return jwtUtil.generateToken(user.getEmail());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}