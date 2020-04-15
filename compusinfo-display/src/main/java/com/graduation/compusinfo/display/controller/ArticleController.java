package com.graduation.compusinfo.display.controller;


import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserLikeService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.HotPostType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/indexArticles")
    @ApiOperation("首页热门/最新文章加载")
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

    @GetMapping("/single")
    @ApiOperation("前台用户查看文章")
    public String toSinglePage(@RequestParam Integer arti,
                               @RequestParam Integer userId, Model model){
        Article article = articleService.selectArticlalById(arti);
        if(userId ==null ||userId ==-1){
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
        //文章阅读数+1
        article.setViewNum(article.getViewNum()+1);
        articleService.updateById(article);
        model.addAttribute("article",article);
        return "single";
    }

    @PostMapping("/search")
    @ApiOperation("前台用户查询文章")
    public  @ResponseBody
    CommonResponseDto<List<Article>> search(@RequestParam String searchval){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("searchval  {} , ",searchval);
        List<Article> articleList = articleService.getArticalBysearchval(searchval);
//        增加搜索热度+2
        articleList.stream().forEach(article -> {
            redisService.increHotsRank2Redis(HotPostType.SEARCH_HOT_TYPE,article.getId());
        });
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }

    @PostMapping("admin/articleList")
    @ApiOperation("后台用户显示文章类别")
    public  @ResponseBody
    CommonResponseDto<PageInfo<Article>> adminArticleList(
            @RequestParam("userId") Long userId,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("userId  {} , ",userId);
        PageInfo<Article> articleList = articleService.selectAdminArticleList(userId,pageNum,pageSize);
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }

    @GetMapping("all/articleList")
    @ApiOperation("前台分页显示文章列表")
    public  @ResponseBody
    CommonResponseDto<PageInfo<Article>> AllArticleList(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("pageNum  {} ,pageSize:{}",pageNum,pageSize);
        PageInfo<Article> articleList = articleService.getArticleList(pageNum,pageSize);
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }

    @GetMapping("/view")
    @ApiOperation("后台用户查看文章")
    public String toSinglePage(@RequestParam("artId") int artId, Model model){
        log.info("artId:{}",artId);
        Article article = articleService.selectArticlalById(artId);
        model.addAttribute("article",article);
        return "admin-page";
    }

}
