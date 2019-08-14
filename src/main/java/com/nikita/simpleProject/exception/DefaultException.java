package com.nikita.simpleProject.exception;

import com.nikita.simpleProject.dto.ApiResult;
import org.springframework.http.HttpStatus;

public class DefaultException extends RuntimeException {
    private HttpStatus code;
    private ApiResult apiResult;

    public DefaultException(HttpStatus code,String message ) {
        super(message);
        this.code = code;
        this.apiResult = ApiResult.FAIL;
    }

    public ApiResult getApiResult() {
        return apiResult;
    }

    public void setApiResult(ApiResult apiResult) {
        this.apiResult = apiResult;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
