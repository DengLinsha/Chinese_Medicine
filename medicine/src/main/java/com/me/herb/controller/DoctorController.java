package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Doctor;
import com.me.herb.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/update")
    public Result updateDoctorInfo(@RequestBody Doctor doctor) {
        try {
            int i = doctorService.updateDoctorInfo(doctor);
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
    public Result getDoctorByUserId(@PathVariable Integer userId) {
        try {
            Doctor doctor = doctorService.getDoctorByUserId(userId);
            if (doctor == null) {
                doctor = new Doctor();
            }
            return Result.success(doctor);
        } catch (Exception e) {
            return Result.error("查询失败，请稍后再试");
        }
    }

    @GetMapping
    public Result queryAll() {
        List<Doctor> doctor = doctorService.queryAll();
        return Result.success(doctor);
    }
}
