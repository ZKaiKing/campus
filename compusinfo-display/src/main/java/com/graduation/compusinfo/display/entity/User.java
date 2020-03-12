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
 * @author zzkai
 * @date 2019-12-12 15:21:16
 */
@Data
@TableName("camp_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField("`username`")
    private String username;

    @ApiModelProperty(value = "密码，加密存储")
    @TableField("`password`")
    private String password;

    @ApiModelProperty(value = "注册邮箱")
    @TableField("`email`")
    private String email;

    @ApiModelProperty(value = "登录密钥")
    @TableField("`token`")
    private String token;

    @ApiModelProperty(value = "用户角色")
    @TableField("`role_id`")
    private Long roleId;

    @ApiModelProperty(value = "用户角色名称")
    @TableField(exist = false)
    private Long roleName;

    @ApiModelProperty(value = "注册手机号")
    @TableField("`phone`")
    private String phone;

    @ApiModelProperty(value = "性别")
    @TableField("`sex`")
    private Integer sex;

    @ApiModelProperty(value = "用户状态1：激活 0：未激活")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty(value = "用户个性签名")
    @TableField("`user_show`")
    private String userShow;

    @ApiModelProperty(value = "用户主页")
    @TableField("`blog`")
    private String blog;

    @ApiModelProperty(value = "用户头像路径")
    @TableField("`user_img`")
    private String userImg;

    @ApiModelProperty(value = "用户粉丝数")
    @TableField("`fans_num`")
    private Integer fansNum;

    @ApiModelProperty(value = "用户关注数")
    @TableField("`concern_num`")
    private Integer concernNum;

    @ApiModelProperty(value = "注册时间")
    @TableField("`created`")
    private Date created;


}