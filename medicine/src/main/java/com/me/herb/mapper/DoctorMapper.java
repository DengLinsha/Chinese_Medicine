package com.me.herb.mapper;

import com.me.herb.pojo.Doctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> queryAll();
}
