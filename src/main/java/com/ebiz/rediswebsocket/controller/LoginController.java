package com.ebiz.rediswebsocket.controller;

import com.ebiz.rediswebsocket.dto.BaseResponseDTO;
import com.ebiz.rediswebsocket.dto.LoginReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by chengjinyun 2018/7/26
 */
@RestController
public class LoginController extends BaseController{
    @Autowired
    private HashOperations<String, String, String> hashRedis;

    /**
     * 简易登录
     * @param loginReqDTO
     * @return
     */
    @PostMapping("/login")
    private BaseResponseDTO login(@RequestBody LoginReqDTO loginReqDTO){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        String storePwd = hashRedis.get("users:"+loginReqDTO.getUsername(), "password");
        if (loginReqDTO.getPassword().equals(storePwd)){
            logger.info("用户：" + loginReqDTO.getUsername() + "登录成功！");
            baseResponseDTO.success();
        }else{
            baseResponseDTO.fail("用户名或密码错误！");
        }

        return baseResponseDTO;
    }
}
