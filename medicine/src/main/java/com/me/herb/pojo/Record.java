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
}
