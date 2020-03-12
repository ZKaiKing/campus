package com.graduation.compusinfo.display.controller;


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

    @RequestMapping(value = "/single",method = RequestMethod.GET)
    public String toSinglePage(@RequestParam Integer arti, Model model){
        Article article = articleService.selectArticlalById(arti);
        log.info("articleTitle  {} , ",article.getTitle());
//        redisService.savehot2Redis(READ_HOT_TYPE,arti);
        model.addAttribute("article",article);
        return "single";
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto like(@RequestParam int islike){
        log.info("islike  {} , ",islike);
        return new CommonResponseDto().code(0).success(true);
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto like(@RequestParam String searchval){
        log.info("searchval  {} , ",searchval);
//        redisService.savehot2Redis(SEARCH_HOT_TYPE,1);
        return new CommonResponseDto().code(200).success(true);
    }

}
