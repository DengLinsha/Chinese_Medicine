package com.me.herb.mapper;

import com.me.herb.pojo.Diagnostic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiagnosticMapper {
    Diagnostic queryDiagnosticById(int recordId);
}
