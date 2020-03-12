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
@TableName("camp_attention")
@ApiModel(value="Attention对象", description="")
@AllArgsConstructor
public class Attention implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "被关注的人（部门)id")
    @TableField("`attented_user_id`")
    private Long attentedUserId;


}