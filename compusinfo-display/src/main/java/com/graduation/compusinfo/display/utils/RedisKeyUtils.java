package com.graduation.compusinfo.display.utils;

/**
 * @author zzk
 * @date 2020/2/27 23:08
 */
public class RedisKeyUtils {


    //文章排行榜
    public static final String HOT_ARTICLE_RANK = "HOT_ARTICLE_RANK";

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存文章点赞数量的key
    public static final String MAP_KEY_POST_LIKED_COUNT = "MAP_POST_LIKED_COUNT";
//    用户过期时间
    public static final int USER_SAVE_TIME_OUT=3;



    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 33::22
     * @param likedUserId 被点赞的人id
     * @param likedPostId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedUserId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }

}
