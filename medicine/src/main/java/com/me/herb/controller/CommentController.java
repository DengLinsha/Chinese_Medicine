package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Comment;
import com.me.herb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result createComment(@RequestBody Comment comment) {
        int i = commentService.add(comment);
        if (i > 0) {
            return Result.success(true);
        } else {
            return Result.error("发布评论失败，请稍后再试");
        }
    }

    @GetMapping("/{recordId}")
    public Result getCommentList(@PathVariable Integer recordId) {
        List<Comment> commentList = commentService.queryAll(recordId);
        return Result.success(commentList);
    }
}
