package com.etc.hospital.entity;



import lombok.Data;

@Data
public class Doctor {
    private Integer docId;
    private String docName;
    private Integer deptId;
    private String docPhone;
    private Integer jobLevel; //1主任医师 2副主任 3普通医师
    private Integer status; //0在岗 1停诊
    private Integer isDelete;//0正常1删除
}