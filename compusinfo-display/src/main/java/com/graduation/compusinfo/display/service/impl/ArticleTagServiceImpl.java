package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.ArticleTag;
import com.graduation.compusinfo.display.mapper.ArticleMapper;
import com.graduation.compusinfo.display.mapper.ArticleTagMapper;
import com.graduation.compusinfo.display.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangzhengkai
 * @date 2020-03-07 01:25:37
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAllArticleByTagId(Long tagId) {

        List<ArticleTag> articleTags = articleTagMapper.selectList(Wrappers.<ArticleTag>lambdaQuery()
                .eq(ArticleTag::getTagId, tagId));
        List<Article> articleList = Lists.newArrayList();
        articleTags.stream().forEach(articleTag -> {
            Long articleId = articleTag.getArticleId();
            Article article = articleMapper.selectById(articleId);
            articleList.add(article);
        });
        return articleList;
    }
}
