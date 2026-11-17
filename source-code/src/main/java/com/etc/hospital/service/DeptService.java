package com.etc.hospital.service;
import com.etc.hospital.dto.*;
import java.util.Map;

public interface DeptService {
    String addDept(DeptAddDTO dto);
    String updateDept(DeptUpdateDTO dto);
    Map<String,Object> pageList(DeptPageDTO dto);
    String deleteDept(DeptDelDTO dto);
}