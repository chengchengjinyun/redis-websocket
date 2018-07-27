package com.ebiz.rediswebsocket.controller;

import com.ebiz.rediswebsocket.demo.MyWebSocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * created by chengjinyun 2018/7/26
 */
@ServerEndpoint("/worldRoom")
public class PublicChatRoom extends BaseController{
    /**
     * 当前公共聊天室在线总人数
     */
    private static int currentOnlineUsers = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<PublicChatRoom> webSocketSet = new CopyOnWriteArraySet<PublicChatRoom>();
    /**
     * session的id对应的username
     */
    private static Map<String, String> sessionUserInfo = new HashMap<String, String>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功时调用
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        String username = session.getQueryString();
        logger.info(username);
        sessionUserInfo.put(session.getId(), username);
        addOnlineCount();           //在线数加1
        System.out.println(username + "加入聊天室！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("欢迎" + username + "进入聊天室!");
        } catch (IOException e) {
            logger.error("发生IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 获取当前离开的用户的username
        String username =sessionUserInfo.get(this.session.getId());
        webSocketSet.remove(this);  //从set中移除该用户
        subOnlineCount();           //在线数减1
        logger.info(username + "离开聊天室！当前在线人数为"  + getOnlineCount());
        // 群发消息
        for (PublicChatRoom publicChat : webSocketSet) {
            try {
                publicChat.sendMessage("欢送" + username + "离开聊天室！");
            } catch (IOException e) {
                logger.error("发生IO异常");
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);

        //群发消息
        for (PublicChatRoom publicChat : webSocketSet) {
            try {
                publicChat.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (PublicChatRoom publicChat : webSocketSet) {
            try {
                publicChat.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 返回当前聊天室总人数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return currentOnlineUsers;
    }

    /**
     * 当前聊天室增加一人
     */
    public static synchronized void addOnlineCount() {
        currentOnlineUsers++;
    }

    /**
     * 当前聊天室减少一人
     */
    public static synchronized void subOnlineCount() {
        currentOnlineUsers--;
    }
}
