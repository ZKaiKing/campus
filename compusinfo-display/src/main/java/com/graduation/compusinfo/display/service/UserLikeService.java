package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**判断该用户是否点赞过该文章**/
    boolean userLikePostOrNo(Integer userId, Integer arti);

    //获取某个用户所有文章的本周点赞指标
    int selectWeekLikeNumIndicator(List<Article> articleList);

    //获取某个用户所有文章的总点赞指标
    int selectLikeNumAllIndicator(List<Article> articleList);
}
