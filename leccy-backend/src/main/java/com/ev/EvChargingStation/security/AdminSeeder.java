package com.ev.EvChargingStation.security;

import com.ev.EvChargingStation.config.AdminProperties;
import com.ev.EvChargingStation.entity.User;
import com.ev.EvChargingStation.enums.Role;
import com.ev.EvChargingStation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail(adminProperties.getEmail())) {
            return;
        }

        User admin = new User();
        admin.setName(adminProperties.getName());
        admin.setEmail(adminProperties.getEmail());
        admin.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
        admin.setPhone(adminProperties.getPhone());
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);
    }
}