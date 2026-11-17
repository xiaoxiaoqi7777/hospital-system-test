package com.etc.hospital.service;


import com.etc.hospital.dto.*;

import com.etc.hospital.entity.TriageRecord;
import java.util.Map;

public interface TriageService {
    // 新增分诊（含重复校验、特殊字符、多层判断）
    String addTriage(TriageAddDTO dto);

    // 修改分诊状态（状态流转、多分支判断）
    String updateTriageStatus(TriageStatusDTO dto);

    // 分页查询（循环遍历、数据组装）
    Map<String, Object> pageList(TriagePageDTO dto);

    // 根据ID查询详情
    TriageRecord getById(Long id);

    //1.患者退号
    String cancelTriage(CancelTriageDTO dto);
    //2.医生当日分诊数据统计
    Map<String,Object> doctorTriageCount(DoctorCountDTO dto);
}