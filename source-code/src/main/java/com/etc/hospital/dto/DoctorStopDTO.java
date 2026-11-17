package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DoctorStopDTO {
    @NotNull(message = "医生ID不能为空")
    private Integer docId;

    @Min(0)
    @Max(1)
    private Integer status; //0在岗 1停诊
}