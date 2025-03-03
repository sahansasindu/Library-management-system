package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.MemberDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @PostMapping("/adduser")
    public void addUserDetails(@RequestBody MemberDTO memberDTO){
        System.out.println("work");
        System.out.println(memberDTO.getMember_id());
    }


}
