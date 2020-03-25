package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.service.UserLikeService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto like(@RequestParam Long artId,
                           @RequestParam Long userId,
                           @RequestParam boolean isLike){
        log.info("artId  {} ,userId  {} ,isLike:{} ",artId,userId,isLike);
            userLikeService.likePost(artId,userId,isLike);
        return new CommonResponseDto().code(200).success(true);
    }

}
