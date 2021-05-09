package com.newhopemail.common.constant;

public class WareConstant {
    public enum PurchaseCode{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVED(2,"已领取"),
        FINISHED(3,"已完成"),
        EXCEPTION(4,"异常");
        int code;
        String message;
        PurchaseCode(int code,String message){
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
    public enum PurchaseDetailCode{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),
        FINISHED(3,"已完成"),
        FAILED(4,"采购失败");
        int code;
        String message;
        PurchaseDetailCode(int code,String message){
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
