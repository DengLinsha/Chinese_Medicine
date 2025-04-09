package com.me.herb.service;

import com.me.herb.pojo.Comment;

import java.util.List;

public interface CommentService {
    int add(Comment comment);

    List<Comment> queryAll(Integer recordId);

    void delete(Integer commentId);
}
