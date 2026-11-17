package com.etc.hospital.service.impl;

import com.etc.hospital.dto.*;
import com.etc.hospital.entity.Department;
import com.etc.hospital.exception.BusinessException;
import com.etc.hospital.mapper.DepartmentMapper;
import com.etc.hospital.service.DeptService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DepartmentMapper deptMapper;


    // ========== 新增：Setter 方法，专门给单元测试使用 ==========
    public void setDeptMapper(DepartmentMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    /**【黑盒7：思维导图测试】新增科室：查重校验，同名抛异常，合法入库 */
    @Override
    public String addDept(DeptAddDTO dto) {
        //数据库查重
        Department exist = deptMapper.selectByName(dto.getDeptName());
        if(exist != null){
            throw new BusinessException("科室名称已存在");
        }
        Department dept = new Department();
        dept.setDeptName(dto.getDeptName());
        dept.setDeptType(dto.getDeptType());
        dept.setIsDelete(0);
        deptMapper.insert(dept);
        return "新增科室成功";
    }

    /**【白盒6：基本路径测试法】修改科室，原有3个if逻辑完全保留不动 */
    @Override
    public String updateDept(DeptUpdateDTO dto) {
        if(dto.getDeptId() < 1){
            throw new BusinessException("科室ID非法");
        }
        if(dto.getDeptName() != null && dto.getDeptName().length() < 2){
            throw new BusinessException("科室名称太短");
        }
        if(dto.getDeptType() != null && (dto.getDeptType() < 1 || dto.getDeptType() > 2)){
            throw new BusinessException("科室类型错误");
        }
        Department dept = new Department();
        dept.setDeptId(dto.getDeptId());
        dept.setDeptName(dto.getDeptName());
        dept.setDeptType(dto.getDeptType());
        deptMapper.updateById(dept);
        return "修改成功";
    }

    /**【白盒7：目标代码插桩】科室分页，控制台插桩打印日志 */
//    @Override
//    public Map<String, Object> pageList(DeptPageDTO dto) {
//        System.out.println("进入科室分页方法");
//        Map<String,Object> map=new HashMap<>();
//        List<Department> list = deptMapper.selectPage(dto.getDeptName());
//        if(dto.getDeptName() != null){
//            System.out.println("进入姓名筛选分支");
//        }
//        map.put("data",list);
//        return map;
//    }
    @Override
    public Map<String, Object> pageList(DeptPageDTO dto) {
        // 桩1：方法入口
        System.out.println("【插桩】进入科室分页方法");

        Map<String,Object> map=new HashMap<>();
        List<Department> list = deptMapper.selectPage(dto.getDeptName());

        // 桩2：判定前标记
        System.out.println("【插桩】准备判断科室名称是否为空");
        if(dto.getDeptName() != null){
            // 桩3：if成立分支
            System.out.println("【插桩】进入姓名筛选分支，deptName = " + dto.getDeptName());
        }else{
            // 新增桩4：if不成立分支（补全分支监控）
            System.out.println("【插桩】未进入姓名筛选分支，deptName 为 null");
        }

        map.put("data",list);
        // 桩5：方法出口
        System.out.println("【插桩】科室分页方法执行完毕，返回数据");
        return map;
    }


    /**【白盒8：JUnit单元测试】删除科室 */
    @Override
    public String deleteDept(DeptDelDTO dto) {
        if(dto.getDeptId() < 1){
            throw new BusinessException("删除ID错误");
        }
        deptMapper.deleteById(dto.getDeptId());
        return "删除成功";
    }
}