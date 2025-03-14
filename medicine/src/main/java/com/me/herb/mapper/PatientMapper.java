package com.me.herb.mapper;

import com.me.herb.pojo.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper {
    int updatePatientInfo(Patient patient);

    Patient getPatientByUserId(Integer userId);
}
