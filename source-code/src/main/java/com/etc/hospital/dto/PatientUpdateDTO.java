package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PatientUpdateDTO {
    @NotNull(message = "患者ID不能为空")
    private Integer patId;
    @Size(min = 2,max = 12)
    private String patName;
    @Size(min = 18,max = 18)
    private String idCard;
}