package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.mapper.ArticleMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.CommentService;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzk
 * @date 2019-12-05 16:02:42
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLikeService userLikeService;

    @Autowired
    private CommentService commentService;

    //用户文章阅读总指标
    private Integer  ReadNumIndicator=0;

    @Override
    public Article selectArticlalById(Integer arti) {
        Article article = this.getById(arti);
        return article;
    }

    @Override
    public Long addArticle(String title, String content, Long typeId,String titleImgUrl, Long userId) {
        Date date = new Date();
        Article article=new Article(date,date,0,0,0);
        article.setTitle(title);
        article.setContent(content);
        article.setTypeId(typeId);
        article.setUserId(userId);
        article.setImg(titleImgUrl);
        int insert = articleMapper.insert(article);
        if(insert ==1){
            return article.getId();
        }
        return Long.valueOf(0);
    }

    @Override
    public PageInfo<Article> selectAdminArticleList(Long userId,int pageNum,int pageSize) {

        PageInfo<Article> articlePage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getUserId,userId)));

        return articlePage;
    }

    @Override
    public List<Article> getArticalBysearchval(String searchVal) {
        return articleMapper.selectList(Wrappers.<Article>lambdaQuery().like(Article::getTitle,searchVal));
    }

    @Override
    public List<Article> selectHotArticleList() {
//        前往redis获取
        List<Integer> hotArticleRank = redisService.getHotArticleRank();
        List<Article> articleList=new ArrayList<>();
        hotArticleRank.stream().forEach(articleId->{
            Article article = articleMapper.selectById(articleId);
            articleList.add(article);
        });

        return articleList;
    }

    @Override
    public List<Article> selectFastArticleList() {
        List<Article> articleList = articleMapper.selectHotList(10);
        return articleList;
    }

    @Override
    public List<Article> selectArticlesByTypeId(Long typeId) {
        return  articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getTypeId, typeId));
    }

    @Override
    public void updateLikeCount(Article article, Integer value) {
        article.setLikeNum(value);
        articleMapper.updateById(article);
    }

    @Override
    public PageInfo<Article> getArticleList(int pageNum, int pageSize) {
        PageInfo<Article> articlePage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->articleMapper.selectList(Wrappers.<Article>lambdaQuery().orderByDesc(Article::getCreateTime)));
        return articlePage;
    }

    @Override
    public int getCountFromTagId(Long tagId) {
        return articleMapper.getCountFromTagId(tagId);
    }

    @Override
    public PageInfo<Article> getArticlePageByTagId(Long tagId, int pageNum, int pageSize) {
        PageInfo<Article> articlePage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getTagId,tagId)
                        .orderByDesc(Article::getCreateTime)));
        return articlePage;
    }

    @Override
    public Map<String, Integer> getvariousIndicators(Long userId) {
        Map<String,Integer> variousIndicators=new HashMap<>();
        List<Article> articleList = articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getUserId, userId));
        int  WeekLikeNumIndicator= userLikeService.selectWeekLikeNumIndicator(articleList);
        variousIndicators.put("WeekLikeNumIndicator",WeekLikeNumIndicator);
        int  LikeNumSumIndicator= userLikeService.selectLikeNumAllIndicator(articleList);
        variousIndicators.put("LikeNumSumIndicator",LikeNumSumIndicator);
        int  WeekCommentIndicator= commentService.selectWeekCommentIndicator(articleList);
        variousIndicators.put("WeekCommentIndicator",WeekCommentIndicator);
        int  CommentSumIndicator=commentService.selectCommentSumIndicator(articleList);
        variousIndicators.put("CommentSumIndicator",CommentSumIndicator);
        ReadNumIndicator=0;
        articleList.stream().forEach(article -> {
            ReadNumIndicator +=article.getViewNum();
        });
        variousIndicators.put("ReadNumIndicator",ReadNumIndicator);
        return variousIndicators;
    }

    @Override
    public boolean commentNumDecBycommId(Long id) {
        return articleMapper.commentNumDecBycommId(id);
    }


}
