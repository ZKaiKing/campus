package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.common.Result;
import com.graduation.compusinfo.display.entity.Comment;
import com.graduation.compusinfo.display.service.CommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/reply/get",method = RequestMethod.POST)
    public @ResponseBody
    Result<List<Comment>> toSinglePage(@RequestParam("replyCount")  int replyCount,@RequestParam("articleId") Long articleId){
        Result<List<Comment>> talkitem = new Result<>();
        log.info("articleId:  {} ,replyCount:{} ",articleId,replyCount);
        List<Comment> commentList = commentService.getAllCommontsById(articleId, replyCount);
        talkitem.setData(commentList);
        return talkitem;
    }

    @RequestMapping(value = "/reply/add",method = RequestMethod.POST)
    public @ResponseBody
    Result addComment(@RequestParam("userID")  Long userID,
                        @RequestParam("userName") String userName,
                        @RequestParam("articleId")  Long articleId,
                        @RequestParam("content") String content){
        Result result = new Result<>();
        log.info("userID:  {} ,userName:{} ,articleId:{},content:{}",userID,userName,articleId,content);
        boolean sumbsuccess = commentService.addComment(userID, userName, articleId, content);
        return result;
    }

    @RequestMapping(value = "/reply/remove",method = RequestMethod.POST)
    public @ResponseBody
    Result removeComment(@RequestParam("id")  Long id){
        Result result = new Result<>();
        log.info("Comment id:  {} ",id);
        boolean sumbsuccess = commentService.removeComment(id);
        return result;
    }





}
