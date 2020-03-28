package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.mapper.ArticleMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public Article selectArticlalById(Integer arti) {
        return this.getById(arti);
    }

    @Override
    public Long addArticle(String title, String content, Long typeId, Long userId) {
        Date date = new Date();
        Article article=new Article(date,date,Long.valueOf(0),0,0,0,0);
        article.setTitle(title);
        article.setContent(content);
        article.setTypeId(typeId);
        article.setUserId(userId);
        int insert = articleMapper.insert(article);
        if(insert ==1){
            return article.getId();
        }
        return Long.valueOf(0);
    }

    @Override
    public PageInfo<List<Article>> selectAdminArticleList(Long userId,int pageNum,int pageSize) {

        PageInfo<List<Article>> articlePage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getUserId,userId)));
//        List<Article> articleList= articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getUserId,userId));
//        return articleList.size()>0 ? articleList : new ArrayList<>();
        return articlePage;
    }

    @Override
    public List<Article> getArticalBysearchval() {
        return articleMapper.selectList(Wrappers.<Article>lambdaQuery().like(Article::getTitle,"%%"));
    }

    @Override
    public List<Article> selectHotArticleList() {
//        前往redis获取
        List<Integer> hotArticleRank = redisService.getHotArticleRank();
        List<Article> articleList = articleMapper.selectBatchIds(hotArticleRank);
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


}
