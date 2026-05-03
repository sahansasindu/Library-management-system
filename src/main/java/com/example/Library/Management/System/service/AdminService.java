package com.example.Library.Management.System.service;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDTO;

import java.util.List;

public interface AdminService {

    public void addNewMember(MemberDTO memberDTO);

    public List<MemberDTO>getAllMembers();

    public void saveCondition(ConditionDto conditionDto);
}
