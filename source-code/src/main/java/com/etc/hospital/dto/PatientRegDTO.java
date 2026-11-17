package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.*;
@Data
public class PatientRegDTO {
    @NotBlank(message = "患者姓名不能为空")
    @Size(min = 2,max = 12)
    private String patName;

    @NotBlank(message = "身份证不能为空")
    @Size(min = 18,max = 18,message = "身份证必须18位")
    private String idCard;

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号格式错误")
    private String phone;

    @Min(0)@Max(120)
    private Integer age;
    //无patId，主键自增
}