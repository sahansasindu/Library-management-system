package com.example.Library.Management.System.service;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDTO;
import com.example.Library.Management.System.dto.paginate.MemberResponsePaginatedDto;
import com.example.Library.Management.System.dto.response.UserResponseDto;

import java.util.List;

public interface AdminService {

    public void addNewMember(MemberDTO memberDTO);

    public MemberResponsePaginatedDto getAllMembers(int page, int size, String searchText);

    List<UserResponseDto> geyAccountDetails();

    boolean updateAccountState(String memberId, boolean newState);


}
