package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.MemberDTO;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void addNewMember(MemberDTO memberDTO) {
        Member member=new Member(memberDTO.getMember_id(),
                                memberDTO.getFirst_name(),
                                memberDTO.getLast_name(),
                                memberDTO.getAddress(),
                                memberDTO.getDob(),
                                memberDTO.getNic_no(),
                                memberDTO.getPhone_no());
        memberRepository.save(member);
    }
}
