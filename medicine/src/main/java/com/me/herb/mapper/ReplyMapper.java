package com.me.herb.mapper;

import com.me.herb.pojo.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int add(Reply reply);

    List<Reply> queryAll(Integer commentId);

    void delete(Integer replyId);
}
