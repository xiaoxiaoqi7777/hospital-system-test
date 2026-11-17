package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class CancelTriageDTO {
    @NotNull(message = "分诊ID不能为空")
    @Min(value = 1,message = "ID必须大于0")
    private Long triageId;
    //患者身份证，新增身份证格式校验
    @NotNull(message = "身份证不能为空")
    @javax.validation.constraints.Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$",message = "身份证格式错误")
    private String idCard;
}