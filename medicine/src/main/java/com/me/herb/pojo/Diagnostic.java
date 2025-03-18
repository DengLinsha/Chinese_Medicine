package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnostic {
    private Integer diagnosticId;
    private String orders;
    private String prescription;
    private String result;
    private Integer recordId;
}
