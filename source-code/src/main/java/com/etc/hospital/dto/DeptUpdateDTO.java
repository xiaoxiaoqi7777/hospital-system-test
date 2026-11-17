package com.etc.hospital.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DeptUpdateDTO {
    @NotNull
    private Integer deptId;
    @Length(min=2,max=15)
    private String deptName;
    @Min(1)@Max(2)
    private Integer deptType;
}