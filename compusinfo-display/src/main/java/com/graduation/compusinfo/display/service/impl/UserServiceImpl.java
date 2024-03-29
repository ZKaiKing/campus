package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.compusinfo.display.entity.User;
import com.graduation.compusinfo.display.mapper.UserMapper;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zzkai
 * @date 2019-12-12 15:21:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Boolean userRegister(User user) {
        User DBuser = getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getStudentNo, user.getStudentNo()));
        if(DBuser == null){
            user.setCreated(new Date());
            return save(user);
        }
        return false;


    }

    @Override
    public User AdminuserLogin(User user) {
        User DBuser = getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getStudentNo, user.getStudentNo()));
        return DBuser;
    }

    @Override
    public String putUserInfoToRedis(User adminUser) {
        //把user实体类转化成json格式
        String userJoin = JsonUtils.toJson(adminUser);
        String rediskey = redisService.saveUser2Redis(userJoin);
        return rediskey;
    }

    @Override
    public void deleteUserInfoFromRedis(String struuid) {
        redisService.deleteUserFromRedis(struuid);
    }

}
