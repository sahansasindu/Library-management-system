package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @PostMapping("/adduser")
    public void addUserDetails(@RequestBody MemberDto memberDTO){
        adminService.addNewMember(memberDTO);
    }


}