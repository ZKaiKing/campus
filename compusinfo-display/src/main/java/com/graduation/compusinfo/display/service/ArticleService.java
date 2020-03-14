package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:02:42
 */
public interface ArticleService extends IService<Article> {

    /** 查询热度文章*/
    List<Article> selectHotArtical();

    /**通过id查找文章*/
    Article selectArticlalById(Integer arti);

    /**上传文章**/
    Long addArticle(String title, String content, Long typeId, Long userId);
}
