package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.service.impl.AdminServiceImpl;
import com.example.Library.Management.System.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping("/adduser")
    public ResponseEntity<StandardResponse> addUserDetails(@RequestBody MemberDto memberDTO){
        adminService.addNewMember(memberDTO);
        return new ResponseEntity<>(
                new StandardResponse(201,"member was save",memberDTO.getFirst_name()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getallmembers")
    public ResponseEntity<StandardResponse> getAllMembers() {
        List<MemberDto> members = adminService.getAllMembers();
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all members successfully", members)
        );
    }

    @PostMapping("/addinformation")
    public ResponseEntity<StandardResponse>addInformattion(@RequestBody ConditionDto condition){
        adminService.saveCondition(condition);
        return new ResponseEntity<>(
                new StandardResponse(201,"data add sucessfully",null),
                HttpStatus.CREATED
        );
    }


}