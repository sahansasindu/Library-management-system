package com.example.Library.Management.System.service;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.dto.response.ConditionResponseDto;
import com.example.Library.Management.System.dto.response.UserResponseDto;

import java.util.List;

public interface AdminService {

    public void addNewMember(MemberDto memberDTO);

    public List<MemberDto>getAllMembers();

    public void saveCondition(ConditionDto conditionDto);

    List<ConditionResponseDto> getCondition();

    List<UserResponseDto> geyAccountDetails();


    boolean updateAccountState(String memberId, boolean newState);
}
