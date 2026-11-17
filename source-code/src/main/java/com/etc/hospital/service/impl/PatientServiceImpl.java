package com.etc.hospital.service.impl;

import com.etc.hospital.dto.*;
import com.etc.hospital.entity.Patient;
import com.etc.hospital.exception.BusinessException;
import com.etc.hospital.mapper.PatientMapper;
import com.etc.hospital.service.PatientService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {

    @Resource
    private PatientMapper patientMapper;

    /**
     * 患者建档：场景法黑盒，身份证重复校验，重复抛异常，合法入库
     */
    @Override
    public String register(PatientRegDTO dto) {
        //根据身份证查询是否已注册
        Patient existPatient = patientMapper.selectByIdCard(dto.getIdCard());
        if (existPatient != null) {
            throw new BusinessException("身份证已注册");
        }
        Patient patient = new Patient();
        patient.setPatName(dto.getPatName());
        patient.setIdCard(dto.getIdCard());
        patient.setPhone(dto.getPhone());
        patient.setAge(dto.getAge());
        patient.setHasTriage(0);
        patient.setIsDelete(0);
        patientMapper.insert(patient);
        return "患者建档成功";
    }

    /**
     * 修改患者：判定条件覆盖，原有if完整保留，不合法抛异常，合法执行更新
     */
    @Override
    public String updatePatient(PatientUpdateDTO dto) {
        if(dto.getPatId() < 1){
            throw new BusinessException("ID错误");
        }
        if(dto.getIdCard() != null && dto.getIdCard().length() != 18){
            throw new BusinessException("身份证格式错误");
        }
        Patient patient = new Patient();
        patient.setPatId(dto.getPatId());
        patient.setPatName(dto.getPatName());
        patient.setIdCard(dto.getIdCard());
        patientMapper.updateById(patient);
        return "修改成功";
    }

    /**
     * 删除患者：条件组合覆盖，原有判断逻辑不变，不能删抛异常，可删逻辑删除
     */
    @Override
    public String deletePatient(PatientDelDTO dto) {
        Integer id = dto.getPatId();
        Integer triage = dto.getHasTriage();
        if(id < 1){
            throw new BusinessException("编号无效");
        }
        if(triage == 1){
            throw new BusinessException("已产生分诊数据不能删除");
        }
        patientMapper.logicDel(id);
        return "删除成功";
    }

//    /**
//     * 根据ID查询：错误分析法，id>9999返回null制造空指针bug
//     */
//    @Override
//    public Map<String, Object> getById(PatientSearchDTO dto) {
//        Map<String,Object> map=new HashMap<>();
//        Patient patient = patientMapper.selectById(dto.getPatId());
//        if(dto.getPatId() > 9999){
//            return null;
//        }
//        map.put("data",patient);
//        return map;
//    }
////@Override
public Map<String, Object> getById(PatientSearchDTO dto) {
    Map<String,Object> map=new HashMap<>();
    if(dto.getPatId() > 9999){
        map.put("data",null);
        return map;
    }
    Patient patient = patientMapper.selectById(dto.getPatId());
    map.put("data",patient);
    return map;
}
//    /**
//     * 患者分页：JMeter性能测试，查询数据库集合封装返回
//     */
//    @Override
//    public Map<String, Object> page(PatientPageDTO dto) {
//        Map<String,Object> map=new HashMap<>();
//        List<Patient> list = patientMapper.selectPage(dto.getPatName());
//        map.put("list",list);
//        return map;
//    }
    @Override
    public Map<String, Object> page(PatientPageDTO dto) {
        Map<String,Object> map=new HashMap<>();
        // 物理分页，只返回前100条数据，避免一次性加载全表
        List<Patient> list = patientMapper.selectPage(dto.getPatName(), 0, 100);
        map.put("list",list);
        return map;
    }
}