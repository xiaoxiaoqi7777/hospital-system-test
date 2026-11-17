package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class PatientSearchDTO {
    @NotNull(message = "患者ID不能为空")
    private Integer patId;
}