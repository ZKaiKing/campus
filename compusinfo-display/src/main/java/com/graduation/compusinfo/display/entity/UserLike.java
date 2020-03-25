package com.graduation.compusinfo.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzk
 * @date 2020-03-25 10:46:08
 */
@Data
@TableName("capus_user_like")
@ApiModel(value="UserLike对象", description="")
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "点赞用户id")
    private Long likedUserId;

    @ApiModelProperty(value = "点赞文章id")
    private Long likedPostId;

    @ApiModelProperty(value = "是否喜欢（已点赞/取消点赞）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    public UserLike() {}
    public UserLike(Long likedUserId, Long likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}