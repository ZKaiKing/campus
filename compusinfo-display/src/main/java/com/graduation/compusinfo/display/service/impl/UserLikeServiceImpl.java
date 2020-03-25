package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.UserLike;
import com.graduation.compusinfo.display.mapper.UserLikeMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zzk
 * @date 2020-03-25 10:46:08
 */
@Service("userLikeService")
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {

    @Autowired
    RedisService redisService;

    @Autowired
    private  UserLikeMapper userLikeMapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public void likePost(Long artId, Long userId, boolean isLike) {
        if(isLike){
            //修改点赞记录并文章点赞数-1
            redisService.saveLiked2Redis(String.valueOf(userId),String.valueOf(artId));
            redisService.increArticleLikedCount(String.valueOf(artId));
        }else{
            //修改点赞记录并文章点赞数-1
            redisService.unlikeFromRedis(String.valueOf(userId),String.valueOf(artId));
            redisService.decreArticleLikedCount(String.valueOf(artId));
        }
    }
    public void saveToDB(UserLike userLike) {
        userLikeMapper.insert(userLike);
    }

    public UserLike getByUserIdAndPostId(Long userId,Long postId){
        UserLike userLike = userLikeMapper.selectOne(Wrappers.<UserLike>lambdaQuery()
                .eq(UserLike::getLikedPostId, postId)
                .eq(UserLike::getLikedUserId, userId));
        return userLike;
    }

    @Override
    public void saveRedisLikeData2DB() {
        List<UserLike> likeList = redisService.getLikedDataFromRedis();
        likeList.stream().forEach(userLike -> {
            UserLike uk1 = getByUserIdAndPostId(userLike.getLikedUserId(), userLike.getLikedPostId());
            if(uk1 == null){
                saveToDB(uk1);
            }else{
                uk1.setStatus(userLike.getStatus());
                saveToDB(uk1);
            }
        });
    }

    @Override
    public void SaveRedisLikeCount2DB() {
        //获取redis中文章点赞情况
        HashMap<Integer, Integer> likedCountMap = redisService.getLikedCountFromRedis();
        Iterator<Map.Entry<Integer, Integer>> iterator = likedCountMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Integer> entry = iterator.next();
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            Article article = articleService.selectArticlalById(key);
            if(article != null){
                Integer originLikeNum = article.getLikeNum();//数据库原始点赞数
                articleService.updateLikeCount(article,originLikeNum+value);
            }
        }
    }


}
