package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.UpdateUserDetailsDto;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.entity.User;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.repository.UserRepository;
import com.example.Library.Management.System.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void updateprofile(UpdateUserDetailsDto userDetailsDto) {

        // Find the member using userid
        Member member = memberRepository.findById(userDetailsDto.getUserid())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + userDetailsDto.getUserid()));


        User user = userRepository.findByMemberid(member)
                .orElseThrow(() -> new RuntimeException("User not found with member ID: " + userDetailsDto.getUserid()));


        // Update the user details
        if (userDetailsDto.getPassword() != null && !userDetailsDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetailsDto.getPassword()));
        }

        if (userDetailsDto.getEmail() != null && !userDetailsDto.getEmail().isEmpty()) {
            user.setEmail(userDetailsDto.getEmail());
        }

        userRepository.save(user);
        System.out.println("User profile updated successfully!");
    }

}