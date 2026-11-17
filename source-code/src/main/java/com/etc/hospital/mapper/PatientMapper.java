package com.etc.hospital.mapper;

import com.etc.hospital.entity.Patient;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PatientMapper {
    int insert(Patient patient);

    int updateById(Patient patient);

    Patient selectById(Integer patId);

    Patient selectByIdCard(String idCard);

    int logicDel(Integer patId);

    //    List<Patient> selectPage(@Param("name") String patName);
    List<Patient> selectPage(
            @Param("name") String patName,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );
}

