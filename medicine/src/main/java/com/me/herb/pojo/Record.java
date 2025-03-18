package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Integer recordId;
    private Integer patientId;
    private Integer doctorId;
    private String description;
    private String tongue;
    private Integer status;
    private String patientName;
    private String phone;
    private Integer sex;
    private Integer age;
    private String oldHistory;
    private String allergiesHistory;
    private String habits;
    private String doctorName;
    private String hospitalName;
    private String departmentName;
    private Diagnostic diagnostic;
    private Float score;
}
