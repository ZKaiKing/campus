package com.graduation.compusinfo.display.service;

/**
 * @author zzk
 * @date 2020/2/27 23:07
 */
public interface RedisService {

    /**
     *  增加文章热度
     * @param IncrHotTypeint
     * @param PostId
     */
    void savehot2Redis(String IncrHotTypeint,int PostId);


    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 保存用户登录到redis
     * @param userJoin
     * @return
     */
    String saveUser2Redis(String userJoin);
}
