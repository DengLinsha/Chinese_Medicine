package com.me.herb.service;

import com.me.herb.pojo.Diagnostic;
import com.me.herb.pojo.Record;
import com.me.herb.pojo.RecordQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecordService {
    int add(Record record);

    // 分页查询病历列表
    Map<String, Object> getRecordList(RecordQueryDTO recordQueryDTO);

    Record queryRecordById(int recordId);

    int update(Record record);
}
