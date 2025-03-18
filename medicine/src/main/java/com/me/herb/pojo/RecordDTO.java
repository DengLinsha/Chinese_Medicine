package com.me.herb.pojo;

import lombok.Data;

@Data
public class RecordDTO {
    private Integer recordId;
    private String patientName;
    private String phone;
    private Integer age;
    private String description;
    private Integer status;
    private String doctorName;
    private String departmentName;
    private String hospitalName;
}