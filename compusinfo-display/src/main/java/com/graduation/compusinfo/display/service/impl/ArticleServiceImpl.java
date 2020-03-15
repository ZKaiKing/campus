package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.mapper.ArticleMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Article> selectHotArtical() {
        return articleMapper.selectList(Wrappers.<Article>lambdaQuery().like(Article::getTitle,"%%"));
    }

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
    public List<Article> selectAdminArticleList(Long userId) {
        List<Article> articleList= articleMapper.selectList(Wrappers.<Article>lambdaQuery().eq(Article::getUserId,userId));
        return articleList.size()>0 ? articleList : new ArrayList<>();
    }
}
