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
import java.util.Date;

/**
 * @author zhangzk
 * @date 2019-12-05 16:05:11
 */
@Data
@TableName("camp_articletype")
@ApiModel(value="Articletype对象", description="")
@AllArgsConstructor
public class Articletype implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标签类型")
    @TableField("`type_name`")
    private String typeName;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;


}