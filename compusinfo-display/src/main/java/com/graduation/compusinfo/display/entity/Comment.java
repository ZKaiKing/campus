package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Data
@TableName("camp_comment")
@ApiModel(value="Comment对象", description="")
@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论正文")
    @TableField("`content`")
    private String content;

    @ApiModelProperty(value = "文章id")
    @TableField("`article_id`")
    private Long articleId;

    @ApiModelProperty(value = "评论用户id")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    @TableField("`user_name`")
    private String userName;

    @ApiModelProperty(value = "评论用户头像")
    @TableField(exist = false)
    private String img;

    @ApiModelProperty(value = "评论时间")
    @TableField("`create_time`")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private List<ReplyComment> child;

    //总回复数
    @TableField(exist = false)
    public int replytotal;


    public boolean addChild(ReplyComment replyComment){
        if(this.getChild() ==null)
            this.child=new ArrayList<ReplyComment>();
        return this.child.add(replyComment);
    }

    public Comment() {
    }

    /**新增一条评论调用该接口**/
    public Comment(String content, Long articleId, Long userId, String userName, String img, Date createTime) {
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
        this.userName = userName;
        this.img = img;
        this.createTime = createTime;
    }
}