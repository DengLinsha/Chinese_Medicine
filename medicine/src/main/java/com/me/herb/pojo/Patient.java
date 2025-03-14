package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Integer patientId;
    private Integer userId;
    private String name;
    private Integer sex;
    private Integer age;
    private String oldHistory;
    private String allergiesHistory;
    private String habits;
}
