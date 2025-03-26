package com.example.Library.Management.System.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class ConditionDto {


    private double entry_payment;

    private double penalty_cost;
}
