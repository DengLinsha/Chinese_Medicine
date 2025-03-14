package com.me.herb.service;

import com.me.herb.pojo.Doctor;

public interface DoctorService {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);
}
