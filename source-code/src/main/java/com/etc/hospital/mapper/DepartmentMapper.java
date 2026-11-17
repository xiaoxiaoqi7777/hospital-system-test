package com.etc.hospital.mapper;
import com.etc.hospital.entity.Department;
import org.apache.ibatis.annotations.Param;
import java.util.List;
public interface DepartmentMapper {
    int insert(Department dept);
    int updateById(Department dept);
    int deleteById(Integer deptId);
    Department selectById(Integer deptId);
    Department selectByName(String deptName);
    List<Department> selectPage(@Param("name")String deptName);
}