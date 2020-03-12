package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    @Override
    public void savehot2Redis(String IncrHotTypeint,int PostId) {
        int hotnum = (int) HotArticleMap.get(IncrHotTypeint);
        log.info("hotnum="+hotnum);
        redisTemplate.opsForZSet().incrementScore("HOT_ARTICLE_RANK",12,12.3);
    }

    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
//        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
//        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

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
