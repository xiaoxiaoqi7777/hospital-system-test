package com.etc.hospital.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
@Data
public class DoctorAddDTO {
    @NotBlank(message = "医生姓名不能为空")
    @Length(min = 2,max = 10,message = "姓名2~10位字符")
    private String docName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号格式错误")
    private String docPhone;

    @Min(value = 1)@Max(value = 5,message = "科室ID范围1~5")
    private Integer deptId;

    @Min(1)@Max(value = 3,message = "职称：1主任医师 2副主任 3医师")
    private Integer jobLevel;
    //【重点：新增去掉docId，自动生成】
}