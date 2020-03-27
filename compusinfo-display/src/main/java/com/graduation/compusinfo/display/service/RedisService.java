package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.UserLike;

import java.util.HashMap;
import java.util.List;

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
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);


    /**
     * 该文章点赞数+1
     * @param likedPostId
     */
    void increArticleLikedCount(String likedPostId);

    /**
     *该文章点赞数-1
     * @param likedPostId
     */
    void decreArticleLikedCount(String likedPostId);

    /**
     *获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    HashMap<Integer, Integer> getLikedCountFromRedis();

    /**
     * 保存用户登录到redis
     * @param userJoin
     * @return
     */
    String saveUser2Redis(String userJoin);

    /**
     * 删除Redis中用户密钥
     * @param rediskey
     */
    void deleteUserFromRedis(String rediskey);

    /**
     *通过cookie中存储信息查询Redis是否存在该用户
     * @param struuid
     */
    String getUserFromRedis(String struuid);
}
