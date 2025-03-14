package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Patient;
import com.me.herb.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/update")
    public Result updatePatientInfo(@RequestBody Patient patient) {
        try {
            int i = patientService.updatePatientInfo(patient);
            if (i > 0) {
                return Result.success(true);
            } else {
                return Result.error("更新失败，请稍后再试");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("更新失败，请稍后再试");
        }
    }

    @GetMapping("/get/{userId}")
    public Result getPatientByUserId(@PathVariable Integer userId) {
        try {
            Patient patient = patientService.getPatientByUserId(userId);
            if (patient == null) {
                patient = new Patient();
            }
            return Result.success(patient);
        } catch (Exception e) {
            return Result.error("查询失败，请稍后再试");
        }
    }
}
