package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.common.Result;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Comment;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.CommentService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Api(tags = {"Comment"})
@Controller
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/reply/get")
    @ApiOperation("回显文章评论列表")
    public @ResponseBody
    Result<List<Comment>> toSinglePage(@RequestParam("replyCount")  int replyCount,@RequestParam("articleId") Long articleId){
        Result<List<Comment>> talkitem = new Result<>();
        log.info("articleId:  {} ,replyCount:{} ",articleId,replyCount);
        List<Comment> commentList = commentService.getAllCommontsById(articleId, replyCount);
        talkitem.setData(commentList);
        return talkitem;
    }

    @PostMapping("/reply/add")
    @ApiOperation("用户文章评论")
    public @ResponseBody
    Result addComment(@RequestParam("userID")  Long userID,
                        @RequestParam("userName") String userName,
                        @RequestParam("articleId")  Long articleId,
                        @RequestParam("content") String content){
        Result result = new Result<>();
        log.info("userID:  {} ,userName:{} ,articleId:{},content:{}",userID,userName,articleId,content);
        boolean sumbsuccess = commentService.addComment(userID, userName, articleId, content);
        Article article = articleService.getById(articleId);
        //文章评论数+1
        article.setComNum(article.getComNum()+1);
        articleService.save(article);
        return result;
    }

    @PostMapping("/reply/remove")
    @ApiOperation("用户删除评论")
    public @ResponseBody
    Result removeComment(@RequestParam("id")  Long id){
        Result result = new Result<>();
        log.info("Comment id:  {} ",id);
        //文章评论数-1
        articleService.commentNumDecBycommId(id);
        boolean sumbsuccess = commentService.removeComment(id);
        return result;
    }

    @GetMapping("/LastCommentList")
    @ApiOperation("最新评论的文章")
    public @ResponseBody
    CommonResponseDto<List<Article>> LastCommentList(@RequestParam("num") int num){
        CommonResponseDto<List<Article>> commonResponseDto = new CommonResponseDto<>();
        log.info("num:  {} ",num);
        List<Article> lastCommentList = commentService.getLastComment(num);
        commonResponseDto.setData(lastCommentList);
        return commonResponseDto;
    }



}
