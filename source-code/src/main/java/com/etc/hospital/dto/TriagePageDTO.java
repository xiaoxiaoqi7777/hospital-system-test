package com.etc.hospital.dto;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class TriagePageDTO {
    //页码默认1，范围1~9999
    @Min(value = 1, message = "页码必须大于等于1")
    @Max(value = 9999, message = "页码不能超过9999")
    private Integer pageNum = 1;

    //每页条数默认10，范围1~100
    @Min(value = 1, message = "每页条数必须大于等于1")
    @Max(value = 100, message = "每页条数不能超过100")
    private Integer pageSize = 10;

    //2~20字符，空/null代表不筛选
    @Size(min = 2, max = 20, message = "查询姓名长度需2-20位")
    private String patientName;

    //分诊状态：1待接诊、2接诊中、3已结束；null查全量
    @Min(value = 1, message = "分诊状态只能是1、2、3")
    @Max(value = 3, message = "分诊状态只能是1、2、3")
    private Integer triageStatus;
}