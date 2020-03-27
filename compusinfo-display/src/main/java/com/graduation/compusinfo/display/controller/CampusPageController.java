package com.graduation.compusinfo.display.controller;

import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Articletype;
import com.graduation.compusinfo.display.entity.Tag;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.ArticletypeService;
import com.graduation.compusinfo.display.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ArticletypeService articletypeService;

    private int BALANCE_DISPLAY_ARTICLEWORDS  = 70;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toIndexPage(Model model){
//        List<Article> hotArticals = articleService.selectHotArtical();
        List<Tag> allTag = tagService.getAllTag();
        model.addAttribute("tags",allTag);
        return "index";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String toRegisterPage(){
        return "regist";
    }

//    @RequestMapping(value = "/login",method = RequestMethod.GET)
//    public String login(){
//        return "login";
//    }
//
    @RequestMapping(value = "/articleslist",method = RequestMethod.GET)
    public String toLogin(){
        return "articleslist";
    }


//    @RequestMapping(value = "/single",method = RequestMethod.GET)
//    public String toSinglePage(){
//        return "single";
//    }


    @RequestMapping(value = "/categories",method = RequestMethod.GET)
    public String toCategoriesPage(Model model){
        List<Articletype> articletypes = articletypeService.RandomgGetArticleType();
        Map returnMap=new HashMap<String,List<Article>>();
        List<List<Article>> retrunList =new ArrayList<>();
        articletypes.stream().forEach(articletype -> {
            List<Article> typeArticles = articleService.selectArticlesByTypeId(articletype.getId());
            returnMap.put(articletype.getTypeName(),typeArticles);
        });
        model.addAttribute("returnMap",returnMap);
        return "categoriesPage";
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
