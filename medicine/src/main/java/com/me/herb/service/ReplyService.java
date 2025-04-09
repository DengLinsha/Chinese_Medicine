package com.me.herb.service;

import com.me.herb.pojo.Reply;

import java.util.List;

public interface ReplyService {
    int add(Reply reply);

    List<Reply> queryAll(Integer commentId);

    void delete(Integer replyId);
}
