package com.springbootbbs.libs;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {

    public static final String SUCCESS_TAG = "success";

    public static final String MESSAGE_TAG = "message";

    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg) {
        super.put(SUCCESS_TAG, code);
        super.put(MESSAGE_TAG, msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(SUCCESS_TAG, code);
        super.put(MESSAGE_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return success("操作成功");
    }

    public static AjaxResult success(String msg) {
        return success(msg, null);
    }

    public static AjaxResult success(Object data) {
        return success("操作成功", data);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    public static AjaxResult success(Boolean success, String msg) {
        return new AjaxResult(success ? HttpStatus.SUCCESS : HttpStatus.ERROR, msg, null);
    }

    public static AjaxResult success(Boolean success, String msg, Object data) {
        return new AjaxResult(success ? HttpStatus.SUCCESS : HttpStatus.ERROR, msg, data);
    }

    public static AjaxResult failure(String msg) {
        return success(false, msg);
    }

    public static AjaxResult failure(String msg, Object data) {
        return success(false, msg, data);
    }
}
