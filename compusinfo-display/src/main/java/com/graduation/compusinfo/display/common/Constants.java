package com.graduation.compusinfo.display.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzk
 * @date 2020/3/3 12:08
 */
public final class Constants {

    //文章热度类型
    public static final String SEARCH_HOT_TYPE="SEARCH_HOT_NUM";
    public static final String READ_HOT_TYPE="READ_HOT_NUM";

    /**
     *文章热度管理Map
     */
    public static final Map HotArticleMap=new HashMap<String,Integer>(){{
        put(SEARCH_HOT_TYPE,1);//搜素热度添加为1
        put(READ_HOT_TYPE,3);//阅读热度添加3
        }};
}
