package com.etc.hospital.controller;
import com.etc.hospital.dto.*;
import com.etc.hospital.service.PatientService;
import com.etc.hospital.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Resource
    private PatientService patientService;

    /**接口10【黑盒-场景法】患者建档*/
    @PostMapping("/register")
    public Result<String> register(@Validated @RequestBody PatientRegDTO dto){
        return Result.success(patientService.register(dto));
    }
    /**接口11【白盒-判定条件覆盖】修改患者*/
    @PostMapping("/update")
    public Result<String> updatePat(@Validated @RequestBody PatientUpdateDTO dto){
        return Result.success(patientService.updatePatient(dto));
    }
    /**接口12【白盒-条件组合覆盖】删除患者*/
    @PostMapping("/delete")
    public Result<String> delPat(@Validated @RequestBody PatientDelDTO dto){
        return Result.success(patientService.deletePatient(dto));
    }
    /**接口13【专项-错误分析法】单查患者*/
//    @PostMapping("/search")
//    public Result<Map<String,Object>> searchPat(@Validated @RequestBody PatientSearchDTO dto){
//        return Result.success(patientService.getById(dto));
//    }
    @PostMapping("/search")
    public Result<Map<String,Object>> searchPat(@Validated @RequestBody PatientSearchDTO dto){
        // 1. 先拿到Service的返回值
        Map<String,Object> resultMap = patientService.getById(dto);
        // 👇 关键：这行代码不判空，直接调用.get()，会触发空指针！
        System.out.println("测试获取data字段：" + resultMap.get("data"));
        // 2. 再交给Result.success()
        return Result.success(resultMap);
    }

    /**接口14【专项-JMeter性能】患者分页*/
    @PostMapping("/page")
    public Result<Map<String,Object>> patPage(@Validated @RequestBody PatientPageDTO dto){
        return Result.success(patientService.page(dto));
    }
}