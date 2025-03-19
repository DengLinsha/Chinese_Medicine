package com.me.herb.mapper;

import com.me.herb.pojo.Record;
import com.me.herb.pojo.RecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper {
    // 新增病历
    int add(Record record);

    // 查询全部病历信息
    List<RecordDTO> queryAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    // 查询病历总数
    int countRecordList();

    // 查询病历详情
    Record queryRecordById(@Param("recordId") int recordId);

    // 更新病历
    int update(Record record);
}
