package com.me.herb.service.impl;

import com.me.herb.mapper.CommentMapper;
import com.me.herb.pojo.Comment;
import com.me.herb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int add(Comment comment) {
        return commentMapper.add(comment);
    }

    @Override
    public List<Comment> queryAll(Integer recordId) {
        return commentMapper.queryAll(recordId);
    }
}
