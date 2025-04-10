package com.me.herb.service.impl;

import com.me.herb.mapper.RecordMapper;
import com.me.herb.pojo.*;
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
    public Map<String, Object> getRecordList(RecordQueryDTO recordQueryDTO) {
        // 计算偏移量
        int offset = (recordQueryDTO.getPage() - 1) * recordQueryDTO.getPageSize();

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("status", recordQueryDTO.getStatus());
        params.put("offset", offset);
        params.put("pageSize", recordQueryDTO.getPageSize());

        // 查询数据
        List<RecordDTO> recordList = recordMapper.queryAll(params);
        int total = recordMapper.countRecordList(params);

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("recordList", recordList);
        return result;
    }

    @Override
    public Record queryRecordById(int recordId) {
        return recordMapper.queryRecordById(recordId);
    }

    @Override
    public int update(Record record) {
        return recordMapper.update(record);
    }

}
