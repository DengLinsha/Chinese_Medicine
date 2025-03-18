package com.me.herb.service.impl;

import com.me.herb.mapper.DiagnosticMapper;
import com.me.herb.pojo.Diagnostic;
import com.me.herb.service.DiagnosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticServiceImpl implements DiagnosticService {
    @Autowired
    DiagnosticMapper diagnosticMapper;

    @Override
    public Diagnostic queryDiagnosticById(int recordId) {
        return diagnosticMapper.queryDiagnosticById(recordId);
    }
}
