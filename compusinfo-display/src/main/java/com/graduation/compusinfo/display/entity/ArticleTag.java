package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangzhengkai
 * @date 2020-03-07 01:25:37
 */
@Data
@TableName("camp_article_tag")
@ApiModel(value="ArticleTag对象", description="")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文章id")
    @TableField("`article_id`")
    private Long articleId;

    @ApiModelProperty(value = "标签id")
    @TableField("`tag_id`")
    private Long tagId;

    @TableField(exist = false)
    private String tagName;


}