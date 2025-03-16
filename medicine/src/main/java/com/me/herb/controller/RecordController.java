package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Record;
import com.me.herb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @PostMapping("/add")
    public Result add(@RequestBody Record record) {
        int i = recordService.add(record);
        if (i > 0) {
            return Result.success(true);
        } else {
            return Result.error("病历创建失败");
        }
    }
}
