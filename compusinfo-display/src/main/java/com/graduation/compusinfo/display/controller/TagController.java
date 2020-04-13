package com.graduation.compusinfo.display.controller;


import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Tag;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.ArticleTagService;
import com.graduation.compusinfo.display.service.TagService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @GetMapping("/all")
    @ApiOperation("显示文章所有标签")
    public String getAllArticleByTagId(@RequestParam Long tagId, Model model){
        List<Article> articleList = articleTagService.getAllArticleByTagId(tagId);
        log.info("tagId  {} , ",tagId);
        return "articleslist";
    }

    @GetMapping("/list")
    @ApiOperation("显示标签列表页面标签回显")
    public String toElementListPage(Model model){
        //回显所有tag标签给前端页面
        List<Tag> tagList = tagService.getAllTag();
        model.addAttribute("tagList",tagList);
        return "elementlist";
    }

    @GetMapping("/{tagId}/articleList")
    @ApiOperation("根据标签id回显文章")
    public @ResponseBody
    CommonResponseDto<PageInfo<Article>> getTagArticleList(@PathVariable("tagId") Long tagId,
                                                           @RequestParam("pageNum") int pageNum,
                                                           @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("tagId  {} , pageNum:{},pageSize:{}",tagId,pageNum,pageSize);
        PageInfo<Article> articleList = articleService.getArticlePageByTagId(tagId,pageNum,pageSize);
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }

}
