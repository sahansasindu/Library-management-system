package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.dto.response.ConditionResponseDto;
import com.example.Library.Management.System.entity.Condition;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.repository.ConditionRepository;
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

    @Autowired
    private ConditionRepository conditionRepository;

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

    @Override
    public void saveCondition(ConditionDto conditionDto) {
        conditionRepository.save(toCondition(conditionDto));
    }

    @Override
    public List<ConditionResponseDto> getCondition() {
        List<Condition> conditions = conditionRepository.findAll();
        return conditions.stream()
                .map(this::Conditionto)
                .collect(Collectors.toList());
    }


    private Condition toCondition(ConditionDto conditionDto) {
        if (conditionDto == null) throw new RuntimeException("ConditionDto is null");

        return Condition.builder()
                .entry_payment(conditionDto.getEntry_payment())
                .penalty_cost(conditionDto.getPenalty_cost())
                .build();
    }

    private ConditionResponseDto Conditionto(Condition condition) {
        if (condition == null) throw new RuntimeException("null");
        return ConditionResponseDto.builder()
                .entry_payment(condition.getEntry_payment())
                .penalty_cost(condition.getPenalty_cost())
                .build();
    }

}