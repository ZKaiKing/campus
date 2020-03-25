package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zzk
 * @date 2020-03-25 10:46:08
 */
public interface UserLikeService extends IService<UserLike> {

    /**
     * 保存用户点赞信息
     */
    void likePost(Long artId, Long userId, boolean isLike);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void saveRedisLikeData2DB();
    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void SaveRedisLikeCount2DB();
}