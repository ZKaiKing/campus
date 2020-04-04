package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.service.RedisService;
import com.graduation.compusinfo.display.service.UserLikeService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.HotPostType;
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
 * @author zzk
 * @date 2020-03-25 10:46:08
 */
@Api(tags = {"UserLike"})
@Controller
@RequestMapping("/user-like")
@Slf4j
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    RedisService redisService;

    @PostMapping("/like")
    @ApiOperation("文章点赞/取消点赞")
    public  @ResponseBody
    CommonResponseDto like(@RequestParam Long artId,
                           @RequestParam Long userId,
                           @RequestParam boolean isLike){
        log.info("artId  {} ,userId  {} ,isLike:{} ",artId,userId,isLike);
        userLikeService.likePost(artId,userId,isLike);
//        点赞，该文章热度＋3
        if(isLike){
           redisService.increHotsRank2Redis(HotPostType.LIKE_HOT_TYPE,artId);
        }else{
//            取消点赞，该文章热度-3
            redisService.increHotsRank2Redis(HotPostType.UNLIKE_HOT_TYPE,artId);
        }
        return new CommonResponseDto().code(200).success(true);
    }

}
