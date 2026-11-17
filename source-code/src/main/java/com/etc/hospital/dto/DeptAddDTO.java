package com.etc.hospital.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Data
public class DeptAddDTO {
    @NotBlank@Length(min=2,max=15)
    private String deptName;
    @Min(1)@Max(2)
    private Integer deptType;//1门诊 2急诊
}