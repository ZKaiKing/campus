package com.graduation.compusinfo.display.controller;


import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangzk
 * @date 2019-12-05 16:02:42
 */
@Api(tags = {"Article"})
@Controller
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/indexArticles",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto HotArticleList(){
        CommonResponseDto<Map<String,List<Article>>> commonResponseDto = new CommonResponseDto<>();
        List<Article> hotArticleList = articleService.selectHotArticleList(Long.valueOf(32));
        List<Article> fastArticleList = articleService.selectFastArticleList(Long.valueOf(32));
        Map<String,List<Article>> map = new HashMap<String, List<Article>>();
        map.put("hotArticleList",hotArticleList);
        map.put("fastArticleList",fastArticleList);
        commonResponseDto.setData(map);
        return commonResponseDto.code(200).success(true);
    }

    @RequestMapping(value = "/single",method = RequestMethod.GET)
    public String toSinglePage(@RequestParam Integer arti, Model model){
        Article article = articleService.selectArticlalById(arti);
        if(arti >= 5){
            article.setLikeOrNo(true);
        }else{
            article.setLikeOrNo(false);
        }
        log.info("articleTitle  {} , ",article.getTitle());
//        redisService.savehot2Redis(READ_HOT_TYPE,arti);
        model.addAttribute("article",article);
        return "single";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto like(@RequestParam String searchval){
        log.info("searchval  {} , ",searchval);
//        redisService.savehot2Redis(SEARCH_HOT_TYPE,1);
        return new CommonResponseDto().code(200).success(true);
    }

    @RequestMapping(value = "/admin/articleList",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto<PageInfo<List<Article>>> adminArticleList(
            @RequestParam("userId") Long userId,
            @RequestParam("pageNum") int pageNum){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("userId  {} , ",userId);
        PageInfo<List<Article>> articleList = articleService.selectAdminArticleList(userId,pageNum,3);
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }


    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public String toSinglePage(@RequestParam("artId") int artId, Model model){
        log.info("artId:{}",artId);
        Article article = articleService.selectArticlalById(artId);
        model.addAttribute("article",article);
        return "admin-page";
    }

}
