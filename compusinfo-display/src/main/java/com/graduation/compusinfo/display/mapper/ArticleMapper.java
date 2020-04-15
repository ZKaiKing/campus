package com.graduation.compusinfo.display.mapper;

import com.graduation.compusinfo.display.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:02:42
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {

//    获取n条最新文章
    List<Article> selectHotList(@Param("n") int n);
//    通过标签ID获取该标签所属文章
    int getCountFromTagId(@Param("tagId") Long tagId);

    boolean commentNumDecBycommId(@Param("id") Long id);
}
