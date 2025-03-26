package com.example.Library.Management.System.controller;


import com.example.Library.Management.System.dto.UpdateUserDetailsDto;
import com.example.Library.Management.System.service.impl.CommonServiceImpl;
import com.example.Library.Management.System.utill.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommonController {

    @Autowired
    private CommonServiceImpl commonService;

    @PostMapping("/updateprofile")
    public ResponseEntity<StandardResponse> update(@RequestBody UpdateUserDetailsDto userDetailsDto) {
        commonService.updateprofile(userDetailsDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "User profile updated successfully", userDetailsDto.getEmail())
        );
    }
}
