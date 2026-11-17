package com.etc.hospital.dto;



import lombok.Data;
import javax.validation.constraints.*;

@Data
public class TriageAddDTO {
    @NotBlank(message = "患者姓名不能为空")
    @Size(min = 2, max = 20, message = "姓名长度必须在2-20位之间")
    private String patientName;

    @NotBlank(message = "患者手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误，必须为11位有效手机号")
    private String patientPhone;

    @NotBlank(message = "身份证不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$",message = "身份证格式不正确")
    private String idCard;

    @NotNull(message = "患者年龄不能为空")
    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 120, message = "年龄不能大于120")
    private Integer patientAge;

    @NotNull(message = "性别不能为空")
    @Min(value = 1, message = "性别只能为1(男)或2(女)")
    @Max(value = 2, message = "性别只能为1(男)或2(女)")
    private Integer gender;

    @NotNull(message = "分诊科室不能为空")
    @Min(value = 1, message = "科室ID范围为1-4")
    @Max(value = 4, message = "科室ID范围为1-4")
    private Integer departmentId;

    @NotNull(message = "接诊医生不能为空")
    @Min(value = 1, message = "医生ID范围为1-6")
    @Max(value = 6, message = "医生ID范围为1-6")
    private Integer doctorId;

    @Size(max = 200, message = "病症描述不能超过200字")
    private String symptom;
}