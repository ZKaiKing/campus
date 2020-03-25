package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;

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

    /**分页显示列表**/
    PageInfo<List<Article>> selectAdminArticleList(Long userId);

//   热度文章
    List<Article> selectHotArticleList(Long valueOf);

//    最新文章
    List<Article> selectFastArticleList(Long valueOf);

//  通过类型Id获取文章列表
    List<Article> selectArticlesByTypeId(Long id);

//    更新文章点赞数
    void updateLikeCount(Article article, Integer value);
}
