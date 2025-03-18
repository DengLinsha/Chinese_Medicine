package com.me.herb.service.impl;

import com.me.herb.mapper.RecordMapper;
import com.me.herb.pojo.Diagnostic;
import com.me.herb.pojo.Record;
import com.me.herb.pojo.RecordDTO;
import com.me.herb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public int add(Record record) {
        return recordMapper.add(record);
    }

    @Override
    public Map<String, Object> getRecordList(int page, int pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询病历列表
        List<RecordDTO> recordList = recordMapper.queryAll(offset, pageSize);

        // 查询病历总数
        int total = recordMapper.countRecordList();

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("recordList", recordList);
        result.put("total", total);
        return result;
    }

    @Override
    public Record queryRecordById(int recordId) {
        return recordMapper.queryRecordById(recordId);
    }

}
