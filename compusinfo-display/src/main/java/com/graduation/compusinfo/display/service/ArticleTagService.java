package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.ArticleTag;

import java.util.List;

/**
 * @author zhangzhengkai
 * @date 2020-03-07 01:25:37
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<Article> getAllArticleByTagId(Long tagId);
}
