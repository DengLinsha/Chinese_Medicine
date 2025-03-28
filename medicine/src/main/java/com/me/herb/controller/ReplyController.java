package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.Reply;
import com.me.herb.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping
    public Result createReply(@RequestBody Reply reply) {
        int i = replyService.add(reply);
        if (i > 0) {
            return Result.success(true);
        } else {
            return Result.error("回复评论失败，请稍后再试");
        }
    }

    @GetMapping("/{commentId}")
    public Result getReplyList(@PathVariable Integer commentId) {
        List<Reply> replyList = replyService.queryAll(commentId);
        return Result.success(replyList);
    }
}
