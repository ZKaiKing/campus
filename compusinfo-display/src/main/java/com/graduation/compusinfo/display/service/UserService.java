package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zzkai
 * @date 2019-12-12 15:21:16
 */
public interface UserService extends IService<User> {

    /**根据用户信息进行注册**/
    Boolean userRegister(User user);

//    /**根据用户信息登录*/
//    User userLogin(User user);

    /**根据用户信息登录*/
    User AdminuserLogin(User user) ;

   /***将用户信息存储到Redis中*/
    String putUserInfoToRedis(User adminUser);

    /**用户登出，删除Redis中保存的用户信息**/
    void deleteUserInfoFromRedis(String struuid);
}
