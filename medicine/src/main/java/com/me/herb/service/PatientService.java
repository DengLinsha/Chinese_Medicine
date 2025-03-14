package com.me.herb.service;

import com.me.herb.pojo.Patient;

public interface PatientService {
    int updatePatientInfo(Patient patient);

    Patient getPatientByUserId(Integer userId);
}
