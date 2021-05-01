package com.newhopemail.product.exception;
import com.newhopemail.common.exception.BaseCode;
import com.newhopemail.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.newhopemail.product")
public class ProductExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> map=new HashMap<>();
        bindingResult.getFieldErrors().forEach(item->{
            map.put(item.getField(),item.getDefaultMessage());
        });
        return R.error(BaseCode.VALID_EXCEPTION.getCode(),BaseCode.VALID_EXCEPTION.getMessage()).put("message",map);
    }
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        log.error("出现错误：",throwable);
        return R.error(BaseCode.UNKNOWN_EXCEPTION.getCode(), BaseCode.UNKNOWN_EXCEPTION.getMessage());
    }
}
