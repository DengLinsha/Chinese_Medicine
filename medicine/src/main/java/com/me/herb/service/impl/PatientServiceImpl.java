package com.me.herb.service.impl;

import com.me.herb.mapper.PatientMapper;
import com.me.herb.pojo.Patient;
import com.me.herb.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientMapper patientMapper;

    @Override
    public int updatePatientInfo(Patient patient) {
        return patientMapper.updatePatientInfo(patient);
    }

    @Override
    public Patient getPatientByUserId(Integer userId) {
        return patientMapper.getPatientByUserId(userId);
    }
}
