package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.service.ArticleTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * @author zzkai
 * @date 2019-12-12 09:25:48
 */
@Api(tags = {"Tag"})
@Controller
@RequestMapping("/tag")
@Slf4j
public class TagController {

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("/all")
    @ApiOperation("显示文章所有标签")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String getAllArticleByTagId(@RequestParam Long tagId, Model model){
        List<Article> articleList = articleTagService.getAllArticleByTagId(tagId);
        log.info("tagId  {} , ",tagId);
        return "articleslist";
    }

}
