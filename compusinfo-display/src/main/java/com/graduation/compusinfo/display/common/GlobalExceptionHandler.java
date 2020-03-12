package com.graduation.compusinfo.display.common;

import lombok.extern.slf4j.Slf4j;

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
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    //public ReturnVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    //    log.error(e.getMessage(), e);
    //    return new ReturnVO().error(e.getBindingResult().getFieldError().getDefaultMessage());
    //}


    ///** 全局异常捕捉处理*/

    //@ExceptionHandler(Exception.class)
    //public Map handleValidationExceptions(Exception ex){
    //    Map map = new HashMap<>(10);
    //    map.put("code", 200);
    //    map.put("msg", ex.getMessage());
    //    return map;
    //}

}
