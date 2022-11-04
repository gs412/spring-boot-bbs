package com.springbootbbs.libs;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    public static final String SUCCESS_TAG = "success";

    public static final String MESSAGE_TAG = "message";

    public static final String DATA_TAG = "data";

    public Result() {
    }

    public Result(int code, String msg) {
        super.put(SUCCESS_TAG, code);
        super.put(MESSAGE_TAG, msg);
    }

    public Result(int code, String msg, Object data) {
        super.put(SUCCESS_TAG, code);
        super.put(MESSAGE_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static Result success() {
        return success("操作成功");
    }

    public static Result success(String msg) {
        return success(msg, null);
    }

    public static Result success(Object data) {
        return success("操作成功", data);
    }

    public static Result success(String msg, Object data) {
        return new Result(HttpStatus.SUCCESS, msg, data);
    }

    public static Result success(Boolean success, String msg) {
        return new Result(success ? HttpStatus.SUCCESS : HttpStatus.ERROR, msg, null);
    }

    public static Result success(Boolean success, String msg, Object data) {
        return new Result(success ? HttpStatus.SUCCESS : HttpStatus.ERROR, msg, data);
    }

    public static Result failure(String msg) {
        return success(false, msg);
    }

    public static Result failure(String msg, Object data) {
        return success(false, msg, data);
    }
}
