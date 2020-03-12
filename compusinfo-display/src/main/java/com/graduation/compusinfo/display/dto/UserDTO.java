package com.graduation.compusinfo.display.dto;

import com.graduation.compusinfo.display.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zzhengkai
 * @date 2019/12/10 16:51
 */
@Data
@ApiModel(value="User对象", description="用户表")
public class UserDTO {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户姓名不能为空")
    @NotNull(message = "用户姓名不能为空")
    private String username;

    @ApiModelProperty(value = "密码，加密存储")
    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确定密码")
    @NotBlank(message = "确定密码不能为空")
    @NotNull(message = "确定密码不能为空")
    private String repassword;

    @ApiModelProperty(value = "注册手机号")
    @NotBlank(message = "手机号码不能为空")
    @NotNull(message = "手机号码不能为空")
    //@Pattern(regexp ="^[1][3-9][0-9]{9}$", message = "手机号格式有误")
    private String phone;



    public User userdtoConverUser(User user){
        user.setUsername(this.username);
        user.setPassword( DigestUtils.md5DigestAsHex(this.password.getBytes()) );
        user.setPhone(this.phone);
        user.setCreated(new Date());
        return user;
    }

}
