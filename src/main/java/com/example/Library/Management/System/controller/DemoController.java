package com.example.Library.Management.System.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @GetMapping("getadmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String>sayHelloAdmin(){
        System.out.println("HI");
        return ResponseEntity.ok("Hellow");
    }

    @GetMapping("getuser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String>sayHelloUser(){
        System.out.println("HI");
        return ResponseEntity.ok("Hellow");
    }
}
