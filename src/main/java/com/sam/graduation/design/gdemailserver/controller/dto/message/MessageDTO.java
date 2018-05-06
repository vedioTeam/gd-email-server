package com.sam.graduation.design.gdemailserver.controller.dto.message;

/**
 * @author sam199510 273045049@qq.com
 * @version Created Time:2018/5/6 15:51:40
 */
public class MessageDTO {

    private Boolean isSuccess;

    private String message;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
