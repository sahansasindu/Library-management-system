package com.example.Library.Management.System.auth;

import com.example.Library.Management.System.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String memberid;
    private String email;
    private String password;
    private String role;
}
