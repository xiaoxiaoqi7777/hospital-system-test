package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class DeptPageDTO {
    private String deptName;
    @Min(1)
    private Integer pageNum;
    @Min(1)
    private Integer pageSize;
}