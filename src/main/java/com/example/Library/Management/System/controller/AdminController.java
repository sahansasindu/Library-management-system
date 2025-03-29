package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.request.ConditionDto;
import com.example.Library.Management.System.dto.MemberDto;
import com.example.Library.Management.System.dto.response.ConditionResponseDto;
import com.example.Library.Management.System.dto.response.UserResponseDto;
import com.example.Library.Management.System.service.impl.AdminServiceImpl;
import com.example.Library.Management.System.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getinformation")
    public ResponseEntity<StandardResponse>getInformation(){
        List<ConditionResponseDto>condition=adminService.getCondition();
        return ResponseEntity.ok(
                new StandardResponse(200,"Fetch all details",condition)
        );
    }

    @GetMapping("/getuseraccount")
    public ResponseEntity<StandardResponse>getUserInformation(){
        List<UserResponseDto>userdetails=adminService.geyAccountDetails();
        return ResponseEntity.ok(
                new StandardResponse(200,"Fetch all details",userdetails)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateState(@PathVariable("id") String member_id, @RequestBody Map<String, Boolean> stateRequest) {
        boolean newState = stateRequest.get("active_state"); // Extract active_state from the request body
        boolean updated = adminService.updateAccountState(member_id, newState);

        if (updated) {
            return ResponseEntity.ok(new StandardResponse(200, "User state updated successfully", member_id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(400, "Failed to update user state", member_id));
        }
    }




}