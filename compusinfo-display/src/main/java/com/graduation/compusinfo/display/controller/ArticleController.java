package com.graduation.compusinfo.display.controller;


import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserLikeService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.HotPostType;
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
    private UserLikeService userLikeService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/indexArticles",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto HotArticleList(){
        CommonResponseDto<Map<String,List<Article>>> commonResponseDto = new CommonResponseDto<>();
        List<Article> hotArticleList = articleService.selectHotArticleList();
        List<Article> fastArticleList = articleService.selectFastArticleList();
        Map<String,List<Article>> map = new HashMap<>();
        map.put("hotArticleList",hotArticleList);
        map.put("fastArticleList",fastArticleList);
        commonResponseDto.setData(map);
        return commonResponseDto.code(200).success(true);
    }

    @RequestMapping(value = "/single",method = RequestMethod.GET)
    public String toSinglePage(@RequestParam Integer arti,
                               @RequestParam Integer userId, Model model){
        Article article = articleService.selectArticlalById(arti);
        if(userId ==null){
            article.setLikeOrNo(false);
        }else{
            boolean likePostOrNo = userLikeService.userLikePostOrNo(userId, arti);
            if (likePostOrNo) {
                article.setLikeOrNo(true);
            } else {
                article.setLikeOrNo(false);
            }
        }
        log.info("articleTitle  {} , ",article.getTitle());
        //阅读文章时，该文章热度+1
        redisService.increHotsRank2Redis(HotPostType.READ_HOT_TYPE,Long.valueOf(arti));
        model.addAttribute("article",article);
        return "single";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto<List<Article>> search(@RequestParam String searchval){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("searchval  {} , ",searchval);
        List<Article> articleList = articleService.getArticalBysearchval();
//        增加搜索热度+2
        articleList.stream().forEach(article -> {
            redisService.increHotsRank2Redis(HotPostType.SEARCH_HOT_TYPE,article.getId());
        });
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
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
