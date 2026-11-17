package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class DeptDelDTO {
    @NotNull
    private Integer deptId;
}