package com.graduation.compusinfo.display.utils;

import lombok.Getter;

/**
 * @author zzk
 * @date 2020/3/3 12:08
 */
@Getter
public enum HotPostType {

    UNLIKE_HOT_TYPE(-3,"取消点赞分值"),
    READ_HOT_TYPE(1,"阅读分值"),
    SEARCH_HOT_TYPE(2,"收藏分值"),
    LIKE_HOT_TYPE(3,"点赞分值");

    private Integer score;
    private String msg;

    HotPostType(Integer score,String msg){
        this.score=score;
        this.msg=msg;
    }

}
