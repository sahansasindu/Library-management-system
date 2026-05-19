package com.example.Library.Management.System.config;

import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.entity.User;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@demo.com";
        Optional<User> adminUser = userRepository.findByEmail(adminEmail);

        if (adminUser.isEmpty()) {
            String adminMemberId = "ADMIN_001";
            Member member = memberRepository.findById(adminMemberId).orElse(null);
            
            if (member == null) {
                member = new Member(
                        adminMemberId,
                        "System",
                        "Admin",
                        "Library Admin Address",
                        new Date(System.currentTimeMillis()),
                        "000000000V",
                        "0000000000"
                );
                memberRepository.save(member);
            }

            User user = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .active_state(true)
                    .memberid(member)
                    .build();
                    
            userRepository.save(user);
            System.out.println("Default Admin User created successfully.");
            System.out.println("Email: " + adminEmail);
            System.out.println("Password: admin123");
        }
    }
}
