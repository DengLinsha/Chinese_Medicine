package com.me.herb.service;

import com.me.herb.pojo.Doctor;

import java.util.List;

public interface DoctorService {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> queryAll();
}
