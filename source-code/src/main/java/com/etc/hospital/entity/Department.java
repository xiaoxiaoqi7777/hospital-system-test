package com.etc.hospital.entity;



import lombok.Data;

@Data
public class Department {
    private Integer deptId;
    private String deptName;
    private Integer deptType;//1门诊 2急诊【适配dept/add接口】
    private Integer isDelete;//0正常1删除
}