package com.newhopemail.common.constant;

public class ProductConstant {
    public enum AttrCode{
        ATTR_BASE_TYPE(1,"基本类型"),
        ATTR_SALE_TYPE(0,"销售类型");
        int code;
        String message;
        AttrCode(int code,String message){
            this.code=code;
            this.message=message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
