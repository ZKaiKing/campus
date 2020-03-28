package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangzk
 * @date 2019-12-05 16:02:42
 */
@Data
@TableName("camp_article")
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发布者id")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "标题")
    @TableField("`title`")
    private String title;

    @ApiModelProperty(value = "图片")
    @TableField("`img`")
    private String img;

    @ApiModelProperty(value = "正文")
    @TableField("`content`")
    private String content;

    @ApiModelProperty(value = "标签id")
    @TableField("`tag_id`")
    private String tagId;

    @ApiModelProperty(value = "类型id")
    @TableField("`type_id`")
    private Long typeId;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("`update_time`")
    private Date updateTime;

    @ApiModelProperty(value = "评论id")
    @TableField("`comment_id`")
    private Long commentId;

    @ApiModelProperty(value = "浏览量")
    @TableField("`view_num`")
    private Integer viewNum;

    @ApiModelProperty(value = "评论量")
    @TableField("`com_num`")
    private Integer comNum;

    @ApiModelProperty(value = "热度")
    @TableField("`hot_num`")
    private Integer hotNum;

    @ApiModelProperty(value = "点赞数")
    @TableField("`like_num`")
    private Integer likeNum;


    @TableField(exist = false)
    private boolean likeOrNo;

    public Article(){

    }

    public Article(Date createTime, Date updateTime, Long commentId, Integer viewNum, Integer comNum, Integer hotNum, Integer likeNum) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.commentId = commentId;
        this.viewNum = viewNum;
        this.comNum = comNum;
        this.hotNum = hotNum;
        this.likeNum = likeNum;
    }
}