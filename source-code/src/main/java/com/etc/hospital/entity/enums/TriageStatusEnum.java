package com.etc.hospital.entity.enums;

public enum TriageStatusEnum {
    WAIT(1,"待分诊"),
    ALLOCATED(2,"已分诊"),
    TREATING(3,"就诊中"),
    FINISH(4,"已完成"),
    CANCEL(5,"已取消");

    private Integer code;
    private String desc;
    TriageStatusEnum(Integer code,String desc){
        this.code=code;
        this.desc=desc;
    }
    public Integer getCode(){return code;}
    public String getDesc(){return desc;}
}