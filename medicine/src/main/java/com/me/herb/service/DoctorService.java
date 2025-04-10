package com.me.herb.service;

import com.me.herb.pojo.Doctor;
import com.me.herb.pojo.DoctorDTO;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> queryAll();

    Map<String, Object> queryDoctorList(DoctorDTO doctorDTO);
}
