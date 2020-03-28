package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.UserLike;
import com.graduation.compusinfo.display.utils.HotPostType;

import java.util.HashMap;
import java.util.List;

/**
 * @author zzk
 * @date 2020/2/27 23:07
 */
public interface RedisService {

    /**
     *  增加文章热度
     */
    void increHotsRank2Redis(HotPostType scoreType, Long PostId);


    /**
     * 点赞。状态为1
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);


    /**
     * 该文章点赞数+1
     */
    void increArticleLikedCount(String likedPostId);

    /**
     *该文章点赞数-1
     */
    void decreArticleLikedCount(String likedPostId);

    /**
     *获取Redis中存储的所有点赞数据
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     */
    HashMap<Integer, Integer> getLikedCountFromRedis();

    /**
     * 保存用户登录到redis
     */
    String saveUser2Redis(String userJoin);

    /**
     * 删除Redis中用户密钥
     */
    void deleteUserFromRedis(String rediskey);

    /**
     *通过cookie中存储信息查询Redis是否存在该用户
     */
    String getUserFromRedis(String struuid);

    /**获得热门文章排行榜**/
    List<Integer> getHotArticleRank();
}
