package com.me.herb.service.impl;

import com.me.herb.mapper.RecordMapper;
import com.me.herb.pojo.Record;
import com.me.herb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public int add(Record record) {
        return recordMapper.add(record);
    }
}
