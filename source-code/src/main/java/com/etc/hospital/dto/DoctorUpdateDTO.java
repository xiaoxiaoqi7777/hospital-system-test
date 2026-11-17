package com.etc.hospital.dto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class DoctorUpdateDTO {
    @NotNull(message = "医生ID不能为空")
    private Integer docId; //统一Integer，和实体一致，消除Long→Integer报错
    @Length(min=2,max=10)
    private String docName;
    @Min(1)@Max(5)
    private Integer deptId;
    @Min(1)@Max(3)
    private Integer jobLevel;
}