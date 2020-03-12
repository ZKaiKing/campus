package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Data
@TableName("camp_collect")
@ApiModel(value="Collect对象", description="")
@AllArgsConstructor
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏文章id")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @TableField("`collected_article_id`")
    private Long collectedArticleId;

    @ApiModelProperty(value = "收藏用户id/谁收藏了id")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "收藏备注")
    @TableField("`content`")
    private String content;


}