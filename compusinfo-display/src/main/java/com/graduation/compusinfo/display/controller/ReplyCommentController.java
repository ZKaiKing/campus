package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.common.Result;
import com.graduation.compusinfo.display.service.ReplyCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Api(tags = {"ReplyComment"})
@Controller
@RequestMapping("/replyComment")
@Slf4j
public class ReplyCommentController {

    @Autowired
    private ReplyCommentService replyCommentService;

    @PostMapping("/addreply")
    @ApiOperation("用户回评")
    public @ResponseBody
    Result toSinglePage(@RequestParam("commentId")  Long commentId,
                        @RequestParam("content") String content,
                        @RequestParam("replyUserId")  Long replyUserId,
                        @RequestParam("replyUserName") String replyUserName,
                        @RequestParam("talkId")  Long talkId,
//                        @RequestParam("replyUserImg")String replyUserImg,
                        @RequestParam("talkName") String talkName){
        Result result = new Result<>();
        log.info("commentId:  {} ,content:{}, replyUserId:{},replyUserName:{},talkId:{},talkName:{}",
                commentId,content,replyUserId,replyUserName,talkId,talkName);
        replyCommentService.addReplyComment(commentId,content,replyUserId,replyUserName,talkId,talkName);
        return result;
    }

    @PostMapping("/removereply")
    @ApiOperation("用户删除回评")
    public @ResponseBody
    Result removeComment(@RequestParam("id")  Long id){
        Result result = new Result<>();
        log.info("Comment id:  {} ",id);
        boolean sumbsuccess = replyCommentService.removeReplyComment(id);
        return result;
    }

}
