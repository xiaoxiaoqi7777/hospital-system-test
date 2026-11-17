package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class PatientPageDTO {
    private String patName;
    @Min(1)
    private Integer pageNum;
    @Min(1)
    private Integer pageSize;
}