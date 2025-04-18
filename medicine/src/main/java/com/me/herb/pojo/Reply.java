package com.me.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private Integer replyId;
    private Integer commentId;
    private Integer userId;
    private Integer role;
    private String content;
    private Date createTime;
    private String username;
    private String replyedName;
}
