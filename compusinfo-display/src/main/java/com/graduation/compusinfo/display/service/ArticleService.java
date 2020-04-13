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

    /** 搜索框查询文章**/
    List<Article> getArticalBysearchval();

    /**通过id查找文章*/
    Article selectArticlalById(Integer arti);

    /**上传文章**/
    Long addArticle(String title, String content, Long typeId, Long userId);

    /**分页显示列表**/
    PageInfo<Article> selectAdminArticleList(Long userId,int pageNum,int pageSize);

//   热度文章
    List<Article> selectHotArticleList();

//    最新文章
    List<Article> selectFastArticleList();

//  通过类型Id获取文章列表
    List<Article> selectArticlesByTypeId(Long id);

//    更新文章点赞
    void updateLikeCount(Article article, Integer value);

//获得整个项目的文章清单，分页显示
    PageInfo<Article> getArticleList(int pageNum,int pageSize);

//    通过标签id获取该标签有多少文章
    int getCountFromTagId(Long id);

    //根据标签id获取所属文章，分页显示
    PageInfo<Article> getArticlePageByTagId(Long tagId, int pageNum, int pageSize);
}
