package com.me.herb.mapper;

import com.me.herb.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper {
    // 新增病历
    int add(Record record);
}
