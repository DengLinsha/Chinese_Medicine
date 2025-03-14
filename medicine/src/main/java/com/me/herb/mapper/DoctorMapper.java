package com.me.herb.mapper;

import com.me.herb.pojo.Doctor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoctorMapper {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);
}
