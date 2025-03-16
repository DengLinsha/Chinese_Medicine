package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Integer userId;

    private Integer patientId;
    private String name;
    private Integer sex;
    private Integer age;
    private String oldHistory;
    private String allergiesHistory;
    private String habits;
}
