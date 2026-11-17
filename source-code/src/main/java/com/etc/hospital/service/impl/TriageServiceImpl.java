package com.etc.hospital.service.impl;



import com.etc.hospital.dto.*;
import com.etc.hospital.entity.Department;
import com.etc.hospital.entity.Doctor;
import com.etc.hospital.entity.TriageRecord;
import com.etc.hospital.entity.enums.TriageStatusEnum;
import com.etc.hospital.exception.BusinessException;
import com.etc.hospital.mapper.DepartmentMapper;
import com.etc.hospital.mapper.DoctorMapper;
import com.etc.hospital.mapper.TriageRecordMapper;
import com.etc.hospital.service.TriageService;
import com.etc.hospital.util.SpecialCharUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class TriageServiceImpl implements TriageService {

    @Resource
    private TriageRecordMapper triageRecordMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private DoctorMapper doctorMapper;

    /**
     * 新增分诊：
     * 逻辑：特殊字符校验 → 手机号重复校验 → 科室医生关联校验 → 多层分支判断 → 新增数据
     * 包含：if多层嵌套、条件分支、布尔判断
     */
    @Override
    public String addTriage(TriageAddDTO dto) {
        // 1. 特殊字符过滤（全字段检测）
        boolean nameChar = SpecialCharUtil.hasSpecialChar(dto.getPatientName());
        boolean sympChar = SpecialCharUtil.hasSpecialChar(dto.getSymptom());
        if (nameChar || sympChar) {
            throw new BusinessException("输入内容包含非法特殊字符，禁止提交");
        }

        // 2. 手机号重复校验：同一手机号当日重复分诊拦截
        TriageRecord exist = triageRecordMapper.selectByPhone(dto.getPatientPhone());
        if (exist != null) {
            // 多层分支：判断原有记录状态
            if (exist.getTriageStatus() != 5) {
                throw new BusinessException("该患者已存在有效分诊记录，请勿重复登记");
            }
        }

        // 3. 科室、医生合法性校验（多层if）
        Department dept = departmentMapper.selectById(dto.getDepartmentId());
        if (dept == null) {
            throw new BusinessException("所选科室不存在");
        }
        Doctor doctor = doctorMapper.selectById(dto.getDoctorId());
        if (doctor == null) {
            throw new BusinessException("所选医生不存在");
        }
        // 二次分支：医生所属科室和选择科室必须一致
        if (!doctor.getDeptId().equals(dto.getDepartmentId())) {
            throw new BusinessException("该医生不属于所选科室，分诊失败");
        }

        // 4. 年龄&性别附加业务判断（多层条件）
        String ageTip = "";
        if (dto.getPatientAge() < 3) {
            if (dto.getGender() == 1) {
                ageTip = "男婴患者";
            } else {
                ageTip = "女婴患者";
            }
        } else if (dto.getPatientAge() >= 60) {
            ageTip = "老年患者，请优先安排就诊";
        } else {
            ageTip = "普通成年患者";
        }

        // 5. 封装数据入库
        TriageRecord record = new TriageRecord();
        record.setPatientName(dto.getPatientName());
        record.setPatientPhone(dto.getPatientPhone());
        record.setPatientAge(dto.getPatientAge());
        record.setGender(dto.getGender());
        record.setDepartmentId(dto.getDepartmentId());
        record.setDoctorId(dto.getDoctorId());
        record.setSymptom(dto.getSymptom());
        record.setTriageStatus(1); // 默认待分诊
        record.setIsDelete(0);
        record.setIdCard(dto.getIdCard());
        record.setTriageFee(new BigDecimal("15.00"));
        triageRecordMapper.insert(record);

        return "分诊登记成功，患者类型：" + ageTip;
    }

    /**
     * 状态变更：严格状态流转（状态机逻辑），大量分支判断
     * 状态规则：
     * 1待分诊 → 2已分诊 / 5取消
     * 2已分诊 → 3就诊中 /5取消
     * 3就诊中 →4已完成 /5取消
     * 4已完成/5取消：不可再变更
     */
    @Override
    public String updateTriageStatus(TriageStatusDTO dto) {
        TriageRecord record = triageRecordMapper.selectById(dto.getId());
        if (record == null || record.getIsDelete() == 1) {
            throw new BusinessException("分诊记录不存在");
        }

        Integer oldStatus = record.getTriageStatus();
        Integer newStatus = dto.getTriageStatus();

        // 终态拦截：已完成/已取消 禁止修改
        if (oldStatus == 4 || oldStatus == 5) {
            throw new BusinessException("当前状态为终态，无法再次修改");
        }

        // 多层分支：状态流转校验
        boolean canChange = false;
        if (oldStatus == 1) {
            if (newStatus == 2 || newStatus == 5) {
                canChange = true;
            }
        } else if (oldStatus == 2) {
            if (newStatus == 3 || newStatus == 5) {
                canChange = true;
            }
        } else if (oldStatus == 3) {
            if (newStatus == 4 || newStatus == 5) {
                canChange = true;
            }
        }

        if (!canChange) {
            throw new BusinessException("状态流转非法，不支持当前变更操作");
        }

        // 执行更新
        triageRecordMapper.updateStatus(dto.getId(), newStatus);
        return "分诊状态修改成功";
    }

    /**
     * 分页查询：for循环遍历组装数据，循环+分支结合
     */
    @Override
    public Map<String, Object> pageList(TriagePageDTO dto) {
        int start = (dto.getPageNum() - 1) * dto.getPageSize();
        List<TriageRecord> dataList = triageRecordMapper.selectPage(start, dto.getPageSize(), dto.getPatientName(), dto.getTriageStatus());
        int total = triageRecordMapper.selectCount(dto.getPatientName(), dto.getTriageStatus());

        // for循环：遍历列表，补充状态文字（循环+内部if分支）
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (TriageRecord r : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getId());
            map.put("patientName", r.getPatientName());
            map.put("phone", r.getPatientPhone());
            map.put("age", r.getPatientAge());
            map.put("gender", r.getGender() == 1 ? "男" : "女");
            map.put("symptom", r.getSymptom());

            // 分支判断状态文本
            String statusText = "";
            switch (r.getTriageStatus()) {
                case 1: statusText = "待分诊"; break;
                case 2: statusText = "已分诊"; break;
                case 3: statusText = "就诊中"; break;
                case 4: statusText = "已完成"; break;
                case 5: statusText = "已取消"; break;
                default: statusText = "未知状态";
            }
            map.put("triageStatusText", statusText);
            resultList.add(map);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("pageNum", dto.getPageNum());
        res.put("pageSize", dto.getPageSize());
        res.put("list", resultList);
        return res;
    }

    @Override
    public TriageRecord getById(Long id) {
        return triageRecordMapper.selectById(id);
    }

    /**
     * 退号业务：多层条件限制
     * 规则：
     * 1、记录不存在/已删除不能退
     * 2、状态已完成、已取消不能退
     * 3、上午8:00-12:00、下午14:00-17:00禁止退号
     * 4、身份证和数据库存储不一致，禁止退号
     */
    @Override
    public String cancelTriage(CancelTriageDTO dto) {
        //1.查询记录
        TriageRecord record = triageRecordMapper.selectById(dto.getTriageId());
        //分支1：记录不存在
        if(record == null || record.getIsDelete() ==1){
            throw new BusinessException("分诊记录不存在，无法退号");
        }
        //分支2：终态无法取消
        Integer status = record.getTriageStatus();
        if(status == TriageStatusEnum.FINISH.getCode() || status == TriageStatusEnum.CANCEL.getCode()){
            throw new BusinessException("已就诊完成或已取消，不能退号");
        }
        //分支3：身份证校验
        if(!record.getIdCard().equals(dto.getIdCard())){
            throw new BusinessException("身份证信息不符，退号失败");
        }
        //分支4：时段限制
        LocalTime now = LocalTime.now();
        boolean forbidCancel = false;
        if((now.isAfter(LocalTime.of(8,0)) && now.isBefore(LocalTime.of(12,0)))
                || (now.isAfter(LocalTime.of(14,0)) && now.isBefore(LocalTime.of(17,0)))){
            forbidCancel = true;
        }
        if(forbidCancel){
            throw new BusinessException("当前就诊高峰时段，禁止办理退号");
        }
        //执行退号
        triageRecordMapper.cancelTriage(dto.getTriageId());
        return "退号成功，挂号费15元原路退回";
    }

    /**
     * 医生分诊统计：for循环汇总各类状态数量，大量分支
     */
    @Override
    public Map<String, Object> doctorTriageCount(DoctorCountDTO dto) {
        //默认当天
        String selectDay = dto.getSelectDate();
        if(selectDay == null || selectDay.trim().length()==0){
            selectDay = LocalDate.now().toString();
        }
        List<TriageRecord> allList = triageRecordMapper.listByDoctorAndDate(dto.getDoctorId(),selectDay);
        //定义统计变量
        int waitNum=0,allocatedNum=0,treatingNum=0,finishNum=0,cancelNum=0;
        //for循环遍历所有数据
        for(TriageRecord item:allList){
            switch (item.getTriageStatus()){
                case 1:waitNum++;break;
                case 2:allocatedNum++;break;
                case 3:treatingNum++;break;
                case 4:finishNum++;break;
                case 5:cancelNum++;break;
                default:
                    //异常未知状态统计
                    break;
            }
        }
        //多层if判断医生工作量评级
        String workLevel;
        int total = allList.size();
        if(total<5){
            workLevel = "空闲";
        }else if(total>=5 && total<15){
            workLevel = "正常接诊";
        }else if(total>=15 && total<30){
            workLevel = "工作量偏大";
        }else{
            workLevel = "超负荷接诊";
        }
        //封装返回
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",total);
        map.put("waitNum",waitNum);
        map.put("allocatedNum",allocatedNum);
        map.put("treatingNum",treatingNum);
        map.put("finishNum",finishNum);
        map.put("cancelNum",cancelNum);
        map.put("workLevel",workLevel);
        map.put("queryDate",selectDay);
        return map;
    }


}