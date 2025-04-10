package com.me.herb.mapper;

import com.me.herb.pojo.Doctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DoctorMapper {
    int updateDoctorInfo(Doctor doctor);

    Doctor getDoctorByUserId(Integer userId);

    List<Doctor> queryAll();

    List<Doctor> selectDoctorList(Map<String, Object> params);

    int countDoctorList(Map<String, Object> params);
}
