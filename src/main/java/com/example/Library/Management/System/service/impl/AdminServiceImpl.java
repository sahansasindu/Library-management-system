package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void addNewMember(MemberDto memberDTO) {
        Member member=new Member(memberDTO.getMember_id(),
                memberDTO.getFirst_name(),
                memberDTO.getLast_name(),
                memberDTO.getAddress(),
                memberDTO.getDob(),
                memberDTO.getNic_no(),
                memberDTO.getPhone_no());
        memberRepository.save(member);
    }

    @Override
    public List<MemberDto> getAllMembers() {
        List<Member>members=memberRepository.findAll();
        return members.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private MemberDto mapToDto(Member member){
        MemberDto memberDto=new MemberDto();
        memberDto.setMember_id(member.getMember_id());
        memberDto.setFirst_name(member.getFirst_name());
        memberDto.setLast_name(member.getLast_name());
        memberDto.setAddress(member.getAddress());
        memberDto.setDob(member.getDob());
        memberDto.setNic_no(member.getNic_no());
        memberDto.setPhone_no(member.getPhone_no());
        return memberDto;
    }
}