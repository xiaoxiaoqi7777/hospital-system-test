package com.etc.hospital.service;
import com.etc.hospital.dto.*;
import java.util.Map;

public interface DoctorService {
    String addDoctor(DoctorAddDTO dto);
    String updateDoctor(DoctorUpdateDTO dto);
    Map<String,Object> pageList(DoctorPageDTO dto);
    String stopDoctor(DoctorStopDTO dto);
}