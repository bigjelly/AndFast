package com.andfast.app.net;

/**
 * Created by mby on 17-8-5.
 */

public class ResultResponse <T> {

    public String has_more;
    public String message;
    public boolean success;
    public int error_code;
    public T data;

    public ResultResponse(String more, int code,boolean success,String _message, T result) {
        error_code = code;
        has_more = more;
        message = _message;
        data = result;
        this.success = success;
    }
}
