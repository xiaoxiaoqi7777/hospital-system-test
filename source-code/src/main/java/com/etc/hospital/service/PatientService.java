package com.etc.hospital.service;
import com.etc.hospital.dto.*;
import java.util.Map;

public interface PatientService {
    String register(PatientRegDTO dto);
    String updatePatient(PatientUpdateDTO dto);
    String deletePatient(PatientDelDTO dto);
    Map<String,Object> getById(PatientSearchDTO dto);
    Map<String,Object> page(PatientPageDTO dto);
}