package com.graduation.compusinfo.display.controller;

import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Tag;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author zzhengkai
 * @date 2019/10/17 17:16
 */

@Controller
@RequestMapping("/campus")
public class CampusPageController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    private int BALANCE_DISPLAY_ARTICLEWORDS  = 70;


    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String toAdminPage(Model model){
//        List<Article> hotArticals = articleService.selectHotArtical();
//        hotArticals.stream().forEach(article->{
////            if(article.getArtContent().length() > BALANCE_DISPLAY_ARTICLEWORDS){
////                article.setArtContent(article.getArtContent().substring(0,70)+"...");
////            }
////           });
//        Article article = new Article();
//        List<Tag> allTag = tagService.getAllTag();
//        model.addAttribute("tags",allTag);
//        model.addAttribute("hotArticals",hotArticals);
//        System.out.println(hotArticals.stream().findFirst().toString());
        return "admin-index";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toIndexPage(Model model){
        List<Article> hotArticals = articleService.selectHotArtical();
//        hotArticals.stream().forEach(article->{
////            if(article.getArtContent().length() > BALANCE_DISPLAY_ARTICLEWORDS){
////                article.setArtContent(article.getArtContent().substring(0,70)+"...");
////            }
////           });
//        Article article = new Article();
        List<Tag> allTag = tagService.getAllTag();
        model.addAttribute("tags",allTag);
        model.addAttribute("hotArticals",hotArticals);
//        System.out.println(hotArticals.stream().findFirst().toString());
        return "index";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String toRegisterPage(){
        return "regist";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }


    @RequestMapping(value = "/single",method = RequestMethod.GET)
    public String toSinglePage(){
        return "single";
    }

    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    public String toCategoriesPage(){
        return "categoriesPage";
    }

    @RequestMapping(value = "/articleslist",method = RequestMethod.GET)
    public String toArticlesListPage(){
        return "articleslist";
    }
    @RequestMapping(value = "/faq",method = RequestMethod.GET)
    public String toFaqPage(){
        return "faq";
    }

    @RequestMapping(value = "/elements",method = RequestMethod.GET)
    public String toElementsPage(){
        return "elements";
    }

    @RequestMapping(value = "/samplepage",method = RequestMethod.GET)
    public String toSamplepagePage(){
        return "samplepage";
    }

    @RequestMapping(value = "/contact",method = RequestMethod.GET)
    public String toContactPage(){
        return "contact";
    }

}
