package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Integer userId;

    private Integer doctorId;
    private String name;
    private String cityName;
    private String hospitalName;
    private String departmentName;
    private String introduction;
}
