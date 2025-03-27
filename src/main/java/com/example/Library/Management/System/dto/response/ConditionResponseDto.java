package com.example.Library.Management.System.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class ConditionResponseDto {
    private double entry_payment;

    private double penalty_cost;
}
