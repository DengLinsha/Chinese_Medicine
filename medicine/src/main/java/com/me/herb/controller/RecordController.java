package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Diagnostic;
import com.me.herb.pojo.DoctorDTO;
import com.me.herb.pojo.Record;
import com.me.herb.pojo.RecordQueryDTO;
import com.me.herb.service.DiagnosticService;
import com.me.herb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private DiagnosticService diagnosticService;

    @PostMapping("/add")
    public Result add(@RequestBody Record record) {
        int i = recordService.add(record);
        if (i > 0) {
            return Result.success(true);
        } else {
            return Result.error("病历创建失败");
        }
    }

    @PostMapping("/page")
    public Result getRecordList(@RequestBody RecordQueryDTO recordQueryDTO) {
        System.out.println(recordQueryDTO.toString());
        Map<String, Object> result = recordService.getRecordList(recordQueryDTO);
        return Result.success(result);
    }

    @GetMapping("/{recordId}")
    public Result getRecordById(@PathVariable int recordId) {
        Record record = recordService.queryRecordById(recordId);
        Diagnostic diagnostic = diagnosticService.queryDiagnosticById(recordId);
        record.setDiagnostic(diagnostic);
        return Result.success(record);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Record record) {
        int i = recordService.update(record);
        if (i > 0) {
            return Result.success(true);
        } else {
            return Result.error("更新失败");
        }
    }
}
