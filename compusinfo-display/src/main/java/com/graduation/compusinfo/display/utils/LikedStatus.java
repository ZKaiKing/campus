package com.graduation.compusinfo.display.utils;

import lombok.Getter;

/**
 * @author zzk
 * @date 2020/3/25 10:55
 */
@Getter
public enum LikedStatus {
    Like(1,"点赞"),
    UNLike(0,"取消点赞");

    private Integer status;

    private String msg;

    LikedStatus(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }


}
