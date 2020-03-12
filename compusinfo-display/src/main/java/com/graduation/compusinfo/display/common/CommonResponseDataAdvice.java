package com.graduation.compusinfo.display.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author zzhengkai
 * @date 2019/12/6 18:02
 */
/**
 * 全局返回值统一封装
 */
//@ControllerAdvice()
    public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String requestPath = request.getURI().getPath();
        System.out.println("---->>>>>"+body);
        // o is null -> return response
        if (body == null) {
            return Result.success();
        }
        // o is instanceof ConmmonResponse -> return o
        if (body instanceof Result) {
            return (Result<Object>) body;
        }
         //string 特殊处理
        if (body instanceof String) {
            return body;
        }
        return Result.success(body);
    }





}
