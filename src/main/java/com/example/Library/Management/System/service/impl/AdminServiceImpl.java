package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.MemberDTO;
import com.example.Library.Management.System.dto.paginate.MemberResponsePaginatedDto;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.repository.MemberRepository;
import com.example.Library.Management.System.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public MemberResponsePaginatedDto getAllMembers(int page, int size, String searchText) {
        Page<Member> memberPage;
        if (searchText == null || searchText.isEmpty()) {
            memberPage = memberRepository.findAll(PageRequest.of(page, size));
        } else {
            memberPage = memberRepository.searchMembers(searchText, PageRequest.of(page, size));
        }

        return MemberResponsePaginatedDto.builder()
                .dataCount(memberPage.getTotalElements())
                .dataList(memberPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList()))
                .build();
    }
    
    private MemberDTO mapToDto(Member member){
        MemberDTO memberDto=new MemberDTO();
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