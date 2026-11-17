package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class DoctorPageDTO {
    private String docName;
    private Integer jobLevel;
    @Min(1)
    private Integer pageNum;
    @Min(1)
    private Integer pageSize;
}