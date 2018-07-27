package com.ebiz.rediswebsocket.dto;

import lombok.Data;

/**
 * created by chengjinyun 2018/7/26
 */
@Data
public class BaseResponseDTO {
    private static final Integer OK = 0;
    private static final Integer NOT_OK = 1;
    private static final String SUCCESS = "成功";
    private static final String FAIL = "失败";

    private Integer code;
    private String msg;

    public void success(){
        this.code = OK ;
        this.msg = SUCCESS;
    }
    public void success(String msg){
        this.code = OK ;
        this.msg = msg;
    }
    public void fail(String msg){
        this.code = NOT_OK ;
        this.msg = msg;
    }
}
