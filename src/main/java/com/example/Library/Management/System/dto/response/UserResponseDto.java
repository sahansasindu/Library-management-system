package com.example.Library.Management.System.dto.response;

import com.example.Library.Management.System.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserResponseDto {

    private String memberid;
    private String email;
    private String role;
    private Boolean active_state;



}
