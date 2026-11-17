package com.etc.hospital.mapper;



import com.etc.hospital.entity.TriageRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface TriageRecordMapper {
    // 新增分诊记录
    int insert(TriageRecord record);

    // 根据手机号查询（重复校验用）
    TriageRecord selectByPhone(@Param("phone") String phone);

    // 根据ID查询
    TriageRecord selectById(@Param("id") Long id);

    // 更新状态
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    // 分页查询
    List<TriageRecord> selectPage(
            @Param("start") Integer start,
            @Param("pageSize") Integer pageSize,
            @Param("name") String name,
            @Param("status") Integer status
    );

    // 查询总条数
    int selectCount(@Param("name") String name, @Param("status") Integer status);
    int cancelTriage(@Param("id")Long id);

    List<TriageRecord> listByDoctorAndDate(@Param("docId")Integer docId,@Param("date")String date);

}