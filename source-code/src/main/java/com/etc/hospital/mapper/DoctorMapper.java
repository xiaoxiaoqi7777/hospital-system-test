package com.etc.hospital.mapper;
import com.etc.hospital.entity.Doctor;
import org.apache.ibatis.annotations.Param;
import java.util.List;
public interface DoctorMapper {
    int insert(Doctor doctor);
    int updateById(Doctor doctor);
    Doctor selectById(Integer docId);
    List<Doctor> selectPage(@Param("name")String docName,@Param("level")Integer jobLevel);
    int updateStatus(@Param("id")Integer docId,@Param("sta")Integer status);
}