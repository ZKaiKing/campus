package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.UserLike;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.utils.LikedStatus;
import com.graduation.compusinfo.display.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.graduation.compusinfo.display.common.Constants.HotArticleMap;

/**
 * @author zzk
 * @date 2020/2/27 23:07
 */
@Service("redisService")
@Slf4j
public class RedisServiceImpl implements RedisService{

    @Autowired
    RedisTemplate redisTemplate;

//保存热门文章
    @Override
    public void savehot2Redis(String IncrHotTypeint,int PostId) {
        int hotnum = (int) HotArticleMap.get(IncrHotTypeint);
        log.info("hotnum="+hotnum);
        redisTemplate.opsForZSet().incrementScore("HOT_ARTICLE_RANK",12,12.3);
    }
    //点赞
    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatus.Like.getStatus());
    }
    //取消点赞
    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatus.UNLike.getStatus());
    }
//从Redis中删除一条点赞数据
    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }
//该文章点赞数+1
    @Override
    public void increArticleLikedCount(String likedPostId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_POST_LIKED_COUNT, likedPostId, 1);
    }
//该文章点赞数-1
    @Override
    public void decreArticleLikedCount(String likedPostId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_POST_LIKED_COUNT, likedPostId, -1);
    }
//获取Redis中存储的所有点赞数据,后期存储到list中
    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        ArrayList<UserLike> list = new ArrayList<>();
        while (scan.hasNext()){
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            //将用户id::文章id分离开
            String[] split = key.split("::");
            String userId=split[0];
            String articleId=split[1];
            Integer likeStatus= (Integer) entry.getValue();
            //封装成对象
            UserLike userLike = new UserLike(Long.valueOf(userId), Long.valueOf(articleId), likeStatus);
            list.add(userLike);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

//    获取Redis中存储的所有点赞数量.
    @Override
    public HashMap<Integer, Integer> getLikedCountFromRedis() {
        Cursor scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_POST_LIKED_COUNT, ScanOptions.NONE);
//        List<HashMap<Integer, Integer>> list = new ArrayList<HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
        while (scan.hasNext()){
            Map.Entry<Object,Object> map = (Map.Entry<Object, Object>) scan.next();
            String  key = (String) map.getKey();
            resultMap.put(Integer.valueOf(key),(Integer)map.getValue());
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_POST_LIKED_COUNT, key);
        }
        return resultMap;
    }

    //保存用户信息到redis
    @Override
    public String saveUser2Redis(String userJoin) {
        //获取uuid
        UUID uuid = UUID.randomUUID();
        String redisKey="user:"  + uuid;
        //把用户信息存入redis  set（key，value，过期时长，过期格式） 设置三天过期
        redisTemplate.opsForValue().set(redisKey, userJoin, RedisKeyUtils.USER_SAVE_TIME_OUT, TimeUnit.DAYS);
        return redisKey;
    }


}
