package com.me.herb.pojo;

import lombok.Data;

@Data
public class RecordQueryDTO {
    private String status;       // 病历状态
    private String patientId;   // 患者姓名
    private String doctorId;    // 医生姓名
    private Integer page = 1;     // 当前页码
    private Integer pageSize = 5;// 每页条数
}
