package com.graduation.compusinfo.display.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzhengkai
 * @date 2019/12/10 17:18
 */
@Slf4j
//@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 方法参数校验
     */
    ///** 全局异常捕捉处理*/

    @ExceptionHandler(Exception.class)
    public Map handleValidationExceptions(Exception ex){
        Map map = new HashMap<>(10);
        map.put("code", 200);
        map.put("msg", ex.getMessage());
        return map;
    }

}
