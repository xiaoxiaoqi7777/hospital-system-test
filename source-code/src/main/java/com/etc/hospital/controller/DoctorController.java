package com.etc.hospital.controller;
import com.etc.hospital.dto.*;
import com.etc.hospital.service.DoctorService;
import com.etc.hospital.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Resource
    private DoctorService doctorService;

    /**接口6【黑盒-正交实验】新增医生*/
    @PostMapping("/add")
    public Result<String> addDoc(@Validated @RequestBody DoctorAddDTO dto){
        return Result.success(doctorService.addDoctor(dto));
    }
    /**接口7【白盒-判定覆盖】修改医生*/
    @PostMapping("/update")
    public Result<String> updateDoc(@Validated @RequestBody DoctorUpdateDTO dto){
        return Result.success(doctorService.updateDoctor(dto));
    }
    /**接口8【白盒-条件覆盖】医生分页*/
    @PostMapping("/page")
    public Result<Map<String,Object>> docPage(@Validated @RequestBody DoctorPageDTO dto){
        return Result.success(doctorService.pageList(dto));
    }
    /**接口9【专项-Postman全量测试】医生停诊*/
    @PostMapping("/cancel")
    public Result<String> stopDoc(@Validated @RequestBody DoctorStopDTO dto){
        return Result.success(doctorService.stopDoctor(dto));
    }
}