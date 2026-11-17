package com.etc.hospital.controller;



import com.etc.hospital.dto.*;
import com.etc.hospital.service.TriageService;
import com.etc.hospital.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/triage")
public class TriageController {

    @Resource
    private TriageService triageService;

    /**
     * 接口1：新增门诊分诊记录
     * 1.用做等价类划分
     */
    @PostMapping("/add")
    public Result<String> add(@Validated @RequestBody TriageAddDTO dto) {
        String msg = triageService.addTriage(dto);
        return Result.success(msg);
    }

    /**
     * 接口2：修改分诊状态
     *6.状态迁移法
     */
    @PostMapping("/update/status")
    public Result<String> updateStatus(@Validated @RequestBody TriageStatusDTO dto) {
        String msg = triageService.updateTriageStatus(dto);
        return Result.success(msg);
    }

    /**
     * 接口3：分页查询分诊记录
     * 2.边界值分析法
     */
    @PostMapping("/page")
    public Result<Map<String, Object>> page(@Validated @RequestBody TriagePageDTO dto) {
        Map<String, Object> pageData = triageService.pageList(dto);
        return Result.success(pageData);
    }

    /**
     * 接口4：患者退号
     * 3.因果决策表
     */
    @PostMapping("/cancel")
    public Result<String> cancel(@Validated @RequestBody CancelTriageDTO dto){
        String res = triageService.cancelTriage(dto);
        return Result.success(res);
    }

    /**
     * 接口5：医生当日分诊统计
     */
    @PostMapping("/doctor/count")
    public Result<Map<String,Object>> docCount(@Validated @RequestBody DoctorCountDTO dto){
        Map<String,Object> countMap = triageService.doctorTriageCount(dto);
        return Result.success(countMap);
    }

}