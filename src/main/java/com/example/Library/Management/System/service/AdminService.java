package com.example.Library.Management.System.service;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDTO;
import com.example.Library.Management.System.dto.paginate.MemberResponsePaginatedDto;

import java.util.List;

public interface AdminService {

    public void addNewMember(MemberDTO memberDTO);

    public MemberResponsePaginatedDto getAllMembers(int page, int size, String searchText);


}
