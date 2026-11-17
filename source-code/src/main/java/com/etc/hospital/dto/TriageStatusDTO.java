package com.etc.hospital.dto;



import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TriageStatusDTO {
    @NotNull(message = "分诊记录ID不能为空")
    @Min(value = 1, message = "记录ID必须大于0")
    private Long id;

    /**
     * 状态：1待分诊 2已分诊 3就诊中 4已完成 5取消
     */
    @NotNull(message = "分诊状态不能为空")
    @Min(value = 1, message = "状态值范围1-5")
    @Max(value = 5, message = "状态值范围1-5")
    private Integer triageStatus;
}