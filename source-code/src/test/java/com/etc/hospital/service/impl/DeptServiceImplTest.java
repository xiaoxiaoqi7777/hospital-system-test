package com.etc.hospital.service.impl;

import com.etc.hospital.dto.DeptDelDTO;
import com.etc.hospital.entity.Department;
import com.etc.hospital.exception.BusinessException;
import com.etc.hospital.mapper.DepartmentMapper;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * 科室删除 JUnit 单元测试（
 * 纯原生JUnit，手写模拟Mapper，不连接真实数据库
 */
public class DeptServiceImplTest {
    // 手写模拟Mapper
    static class MockDepartmentMapper implements DepartmentMapper {
        private int effectRows;
        public void setEffectRows(int effectRows) {
            this.effectRows = effectRows;
        }
        @Override
        public int insert(Department dept) { return 0; }
        @Override
        public int updateById(Department dept) { return 0; }
        @Override
        public int deleteById(Integer deptId) {
            System.out.println("【模拟Mapper】执行删除操作，删除ID：" + deptId);
            return effectRows;
        }
        @Override
        public Department selectById(Integer deptId) { return null; }
        @Override
        public Department selectByName(String deptName) { return null; }
        @Override
        public List<Department> selectPage(@Param("name") String deptName) { return null; }
    }
    /**
     * 测试场景1：deptId < 1，ID非法，抛出异常
     */
    @Test
    void deleteDept_IdLessOne_ThrowException() {
        System.out.println("===== 开始执行测试用例1：ID非法场景 =====");
        MockDepartmentMapper mockMapper = new MockDepartmentMapper();
        DeptServiceImpl deptService = new DeptServiceImpl();
        deptService.setDeptMapper(mockMapper);
        DeptDelDTO dto = new DeptDelDTO();
        dto.setDeptId(0);
        System.out.println("传入参数 deptId = " + dto.getDeptId());
        BusinessException ex = Assertions.assertThrows(BusinessException.class, () -> {
            deptService.deleteDept(dto);
        });
        Assertions.assertEquals("删除ID错误", ex.getMessage());
        System.out.println("捕获异常：" + ex.getMessage());
        System.out.println("===== 测试用例1 执行结束 =====\n");
    }
    /**
     * 测试场景2：deptId ≥ 1，ID合法，正常删除
     */
    @Test
    void deleteDept_IdNormal_Success() {
        System.out.println("===== 开始执行测试用例2：ID合法场景 =====");
        MockDepartmentMapper mockMapper = new MockDepartmentMapper();
        mockMapper.setEffectRows(1);
        DeptServiceImpl deptService = new DeptServiceImpl();
        deptService.setDeptMapper(mockMapper);
        DeptDelDTO dto = new DeptDelDTO();
        dto.setDeptId(10);
        System.out.println("传入参数 deptId = " + dto.getDeptId());
        String result = deptService.deleteDept(dto);
        System.out.println("方法返回结果：" + result);
        Assertions.assertEquals("删除成功", result);
        System.out.println("===== 测试用例2 执行结束 =====");
    }
}