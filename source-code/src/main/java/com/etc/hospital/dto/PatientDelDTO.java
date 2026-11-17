package com.etc.hospital.dto;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PatientDelDTO {
    @NotNull
    private Integer patId;
    @Min(0)@Max(1)
    private Integer hasTriage; //0未就诊 1已就诊
}