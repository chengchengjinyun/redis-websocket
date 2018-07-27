package com.ebiz.rediswebsocket.controller;

import com.ebiz.rediswebsocket.dto.AddIdolReqDTO;
import com.ebiz.rediswebsocket.dto.BaseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by chengjinyun 2018/7/26
 * 用户关注功能
 */
@RestController
public class FollowController extends BaseController{

    @Autowired
    private SetOperations<String, String> setRedis;

    /**
     * 添加关注
     * @param addIdolReqDTO
     * @return
     */
    @PostMapping("/addIdol")
    private BaseResponseDTO addIdol(@RequestBody AddIdolReqDTO addIdolReqDTO){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        // 获取当前用户的username
        String username = addIdolReqDTO.getUsername();
        // 获取当前用户要关注的人的列表
        String[] myIdols = addIdolReqDTO.getIdolList().toArray(new String[]{});
        try {
            // 将我关注的添加到我的关注列表中
            setRedis.add("myIdols:" + username, myIdols);
            // 将我添加到我关注的偶像粉丝列表中
            for (String idolName : myIdols){
                setRedis.add("myFans:" + idolName , username);
            }
            baseResponseDTO.success("关注成功！");
        } catch (Exception e) {
            baseResponseDTO.fail("关注失败，请刷新重试！");
        }

        return baseResponseDTO;
    }


}
