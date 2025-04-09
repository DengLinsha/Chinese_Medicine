package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer recordId;
    private Integer role;
    private String content;
    private Date createTime;
    private String username;
    private List<Reply> replies;
}
