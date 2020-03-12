package com.graduation.compusinfo.display.common;

import lombok.AllArgsConstructor;

/**
 * @author zzhengkai
 * @date 2019/12/5 18:00
 */
@AllArgsConstructor
public enum Code implements BaseCode{

    SUCCESS(200, "成功"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "无权限访问"),
    NOT_FOUND(404, "找不到对应请求"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

    ERROR(500, "内部错误");

    private final int code;

    private final String msg;

    @Override
    public int getCode() {
        return code;
    }


    @Override
    public String getMsg() {
        return msg;
    }

}
