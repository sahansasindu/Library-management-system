package com.example.Library.Management.System.auth;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.config.JwtService;
import com.example.Library.Management.System.entity.Role;
import com.example.Library.Management.System.entity.User;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    public AuthenticationResponse register(RegisterRequest request) {
        Member member = memberRepository.findById(request.getMemberid())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        var user = User.builder()
                .memberid(member)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active_state(true) // Ensure the user is active when registered
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getActive_state()) {
            throw new RuntimeException("User account is inactive");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .id(user.getMemberid())
                .email(user.getEmail())
                .build();
    }




}
