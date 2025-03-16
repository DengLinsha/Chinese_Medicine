package com.me.herb.service.impl;

import com.me.herb.mapper.DoctorMapper;
import com.me.herb.pojo.Doctor;
import com.me.herb.pojo.User;
import com.me.herb.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public int updateDoctorInfo(Doctor doctor) {
        return doctorMapper.updateDoctorInfo(doctor);
    }

    @Override
    public Doctor getDoctorByUserId(Integer userId) {
        return doctorMapper.getDoctorByUserId(userId);
    }

    @Override
    public List<Doctor> queryAll() {
        return doctorMapper.queryAll();
    }
}
