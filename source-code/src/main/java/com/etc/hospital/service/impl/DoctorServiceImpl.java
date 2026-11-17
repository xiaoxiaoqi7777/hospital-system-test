package com.etc.hospital.service.impl;

import com.etc.hospital.dto.*;
import com.etc.hospital.entity.Doctor;
import com.etc.hospital.exception.BusinessException;
import com.etc.hospital.mapper.DoctorMapper;
import com.etc.hospital.service.DoctorService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private DoctorMapper doctorMapper;

    /**
     * 新增医生：正交实验测试用业务，参数由注解校验，合法入库
     */
    @Override
    public String addDoctor(DoctorAddDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setDocName(dto.getDocName());
        doctor.setDocPhone(dto.getDocPhone());
        doctor.setDeptId(dto.getDeptId());
        doctor.setJobLevel(dto.getJobLevel());
        doctor.setStatus(0);
        doctor.setIsDelete(0);
        //不用setDocId，数据库自增+MyBatis自动赋值
        doctorMapper.insert(doctor);
        System.out.println("入库自动生成ID："+doctor.getDocId());
        return "新增医生成功";
    }

    /**
     * 修改医生：判定覆盖测试，两处if判定，不合法抛异常
     */
    @Override
    public String updateDoctor(DoctorUpdateDTO dto) {
        //判定1：ID小于等于0非法
        if(dto.getDocId() <= 0){
            throw new BusinessException("ID非法");
        }
        //判定2：职称不在1~3区间非法
        if(dto.getJobLevel() != null && (dto.getJobLevel() < 1 || dto.getJobLevel() > 3)){
            throw new BusinessException("职称错误");
        }
        Doctor doctor = new Doctor();
        doctor.setDocId(dto.getDocId());
        doctor.setDocName(dto.getDocName());
        doctor.setDeptId(dto.getDeptId());
        doctor.setJobLevel(dto.getJobLevel());
        doctorMapper.updateById(doctor);
        return "修改成功";
    }

    /**
     * 医生分页：条件覆盖测试，两个独立条件判断
     */
    @Override
    public Map<String, Object> pageList(DoctorPageDTO dto) {
        Map<String,Object> map=new HashMap<>();
        List<Doctor> list = doctorMapper.selectPage(dto.getDocName(),dto.getJobLevel());
        if(dto.getDocName() != null && !dto.getDocName().isEmpty()){
            map.put("name筛选",true);
        }
        if(dto.getJobLevel() != null){
            map.put("职称筛选",true);
        }
        map.put("data",list);
        return map;
    }

    /**
     * 医生启停诊: Postman专项测试接口
     */
    @Override
    public String stopDoctor(DoctorStopDTO dto) {
        Doctor doctor = doctorMapper.selectById(dto.getDocId());
        if(doctor == null){
            throw new BusinessException("目标医生不存在");
        }
        //重复状态拦截
        if(doctor.getStatus().equals(dto.getStatus())){
            throw new BusinessException(dto.getStatus()==1?"该医生已处于停诊状态":"该医生正常出诊中");
        }
        doctorMapper.updateStatus(dto.getDocId(), dto.getStatus());
        return dto.getStatus()==1?"已停诊":"恢复出诊";
    }
}