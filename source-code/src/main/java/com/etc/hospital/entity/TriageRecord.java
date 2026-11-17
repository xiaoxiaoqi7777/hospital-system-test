package com.etc.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class TriageRecord {
    private Long id;
    private String patientName;
    private String patientPhone;
    private Integer patientAge;
    private Integer gender;
    private Integer departmentId;
    private Integer doctorId;
    private String symptom;
    private Integer triageStatus;
    private Date createTime;
    private Date updateTime;
    private Integer isDelete;
    private String idCard;          // 身份证
    private BigDecimal triageFee;   // 挂号费15元

}