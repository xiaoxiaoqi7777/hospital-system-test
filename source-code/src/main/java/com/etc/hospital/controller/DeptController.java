package com.etc.hospital.controller;
import com.etc.hospital.dto.*;
import com.etc.hospital.service.DeptService;
import com.etc.hospital.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    /**接口15【黑盒-思维导图】新增科室*/
    @PostMapping("/add")
    public Result<String> addDept(@Validated @RequestBody DeptAddDTO dto){
        return Result.success(deptService.addDept(dto));
    }
    /**接口16【白盒-基本路径】修改科室*/
    @PostMapping("/update")
    public Result<String> updateDept(@Validated @RequestBody DeptUpdateDTO dto){
        return Result.success(deptService.updateDept(dto));
    }
    /**接口17【白盒-代码插桩】科室分页*/
    @PostMapping("/page")
    public Result<Map<String,Object>> deptPage(@Validated @RequestBody DeptPageDTO dto){
        return Result.success(deptService.pageList(dto));
    }
    /**接口18【白盒-JUnit单元】删除科室*/
    @PostMapping("/delete")
    public Result<String> delDept(@Validated @RequestBody DeptDelDTO dto){
        return Result.success(deptService.deleteDept(dto));
    }
}