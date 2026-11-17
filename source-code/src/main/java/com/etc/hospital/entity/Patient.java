package com.etc.hospital.entity;
import lombok.Data;
@Data
public class Patient {
    private Integer patId;
    private String patName;
    private String idCard;
    private String phone;
    private Integer age;
    private Integer hasTriage;//0无分诊 1已有分诊记录（删除判定用）
    private Integer isDelete;
}