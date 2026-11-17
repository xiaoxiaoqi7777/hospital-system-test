package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DoctorCountDTO {
    @NotNull(message = "医生ID不能为空")
    @Min(1)
    private Integer doctorId;
    //查询日期
    private String selectDate;
}