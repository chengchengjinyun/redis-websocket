package com.ebiz.rediswebsocket.dto;

import lombok.Data;

import java.util.List;

/**
 * created by chengjinyun 2018/7/26
 */
@Data
public class AddIdolReqDTO {
    /**
     * 当前用户的用户名
     */
    private String username;
    /**
     * 当前用户添加的关注列表
     */
    private List<String> idolList;
}
