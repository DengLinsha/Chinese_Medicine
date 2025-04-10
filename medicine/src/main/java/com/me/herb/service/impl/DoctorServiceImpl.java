package com.me.herb.service.impl;

import com.me.herb.mapper.DoctorMapper;
import com.me.herb.pojo.Doctor;
import com.me.herb.pojo.DoctorDTO;
import com.me.herb.pojo.User;
import com.me.herb.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> queryDoctorList(DoctorDTO doctorDTO) {
        // 计算偏移量
        int offset = (doctorDTO.getPage() - 1) * doctorDTO.getPageSize();

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("cityName", doctorDTO.getCityName());
        params.put("hospitalName", doctorDTO.getHospitalName());
        params.put("departmentName", doctorDTO.getDepartmentName());
        params.put("offset", offset);
        params.put("pageSize", doctorDTO.getPageSize());

        // 查询数据
        List<Doctor> doctorList = doctorMapper.selectDoctorList(params);
        int total = doctorMapper.countDoctorList(params);

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("doctorList", doctorList);
        return result;
    }
}
