package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.compusinfo.display.entity.Articletype;

import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:05:11
 */
public interface ArticletypeService extends IService<Articletype> {



    //随机获取文章类型进行分类回显
    List<Articletype> RandomgGetArticleType();
}
