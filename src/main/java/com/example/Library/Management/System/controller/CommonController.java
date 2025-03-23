package com.example.Library.Management.System.controller;


import com.example.Library.Management.System.dto.UpdateUserDetailsDto;
import com.example.Library.Management.System.service.impl.CommonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CommonController {

    @Autowired
    private CommonServiceImpl commonService;

    @PostMapping("/updateprofile")
    public void update(
            @RequestBody UpdateUserDetailsDto userDetailsDto
    ){

        commonService.updateprofile(userDetailsDto);


    }
}
