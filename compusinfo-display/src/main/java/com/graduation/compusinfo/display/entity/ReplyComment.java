package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Data
@TableName("camp_reply_comment")
@ApiModel(value="ReplyComment对象", description="")
public class ReplyComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "顶层评论id")
    @TableField("`comment_id`")
    private Long commentId;

    @ApiModelProperty(value = "上级评论id")
    @TableField("`talk_id`")
    private Long talkId;

    @ApiModelProperty(value = "上级评论用户名称")
    @TableField("`talk_name`")
    private String talkName;

    @ApiModelProperty(value = "评论内容")
    @TableField("`content`")
    private String content;

    @ApiModelProperty(value = "多级评论用户id")
    @TableField("`reply_user_id`")
    private Long replyUserId;

    @ApiModelProperty(value = "多级评论用户头像")
    @TableField(exist = false)
    private String replyUserImg;

    @ApiModelProperty(value = "多级评论用户名称")
    @TableField("`reply_user_name`")
    private String replyUserName;

    @ApiModelProperty(value = "评论时间")
    @TableField("`create_time`")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public ReplyComment() {
    }

    public ReplyComment(Long commentId, Long talkId, String talkName, String content, Long replyUserId, String replyUserImg, String replyUserName, Date createTime) {
        this.commentId = commentId;
        this.talkId = talkId;
        this.talkName = talkName;
        this.content = content;
        this.replyUserId = replyUserId;
        this.replyUserImg = replyUserImg;
        this.replyUserName = replyUserName;
        this.createTime = createTime;
    }
}