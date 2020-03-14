package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.mapper.ArticleMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Article article=new Article();
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
}
