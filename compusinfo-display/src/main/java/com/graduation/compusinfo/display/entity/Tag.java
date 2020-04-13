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
 * @author zzkai
 * @date 2019-12-12 09:25:48
 */
@Data
@TableName("camp_tag")
@ApiModel(value="Tag对象", description="")
@AllArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标签名")
    @TableField("`tagName`")
    private String tagName;

    @ApiModelProperty(value = "标签详情")
    @TableField("`tagDetail`")
    private String tagDetail;

    @TableField(exist = false)
    private int subArticleCount;

    public Tag() {
    }

}