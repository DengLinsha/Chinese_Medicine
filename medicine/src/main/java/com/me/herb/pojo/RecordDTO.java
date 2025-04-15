package com.me.herb.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class RecordDTO {
    private Integer recordId;
    private String patientName;
    private String phone;
    private Integer age;
    private Float score;
    private Date createTime;
    private Date updateTime;
    private String description;
    private Integer status;
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    private String hospitalName;
}