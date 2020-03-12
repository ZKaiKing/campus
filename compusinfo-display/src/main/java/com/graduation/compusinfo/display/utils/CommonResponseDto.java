package com.graduation.compusinfo.display.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzk
 * @date 2020/2/27 13:51
 */
@Data
public class CommonResponseDto<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;

    private boolean success;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 出错的请求路径
     */
    private String path;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public CommonResponseDto code(Integer code){
        this.code = code;
        return this;
    }

    public CommonResponseDto success(boolean success){
        this.success = success;
        return this;
    }




}
