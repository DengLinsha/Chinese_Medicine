package com.me.herb.pojo;

import lombok.Data;

@Data
public class DoctorDTO {
    private String cityName;
    private String hospitalName;
    private String departmentName;
    private Integer page = 1;      // 默认第一页
    private Integer pageSize = 5; // 默认每页10条
}
