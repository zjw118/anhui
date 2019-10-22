package com.gistone.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
public class Jpush2 {

    // 在极光注册上传应用的 appKey 和 masterSecret
    // 必填，例如466f7032ac604e02fb7bda89
    private static final String appKey = "bd765a179f5aefb97a7bbac7";

    // 必填，每个应用都对应一个masterSecret
    private static final String masterSecret = "d1b1b5320aaed3c2bd57a4fb";

    private final static Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    private static JPushClient jpush = null;

    public static void main(String[] args){

        String message = "{\"name\":\"jack\",\"age\":22}";

        // 测试发送消息或者通知
        jPushSend(message);
    }

    public static void jPushSend(String message){

        logger.info("enter jPushSend.");
        jpush = new JPushClient(masterSecret, appKey);

        PushPayload payload = buildPushObject_all_all_alert_message(message);

        PushResult msgResult = null;
        try {
            msgResult = jpush.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        if (null != msgResult) {
            System.out.println(msgResult.getResponseCode());
        } else {
            System.out.println("无法获取数据");
        }
    }
    // setPlatform用于设置平台，setAudience用于设置推送的人，setMessage表示推送的是自定义信息，推送通知可以设置PushPayload.alertAll("test");
    private static PushPayload buildPushObject_all_all_alert_message(String message) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all()).setMessage(Message.newBuilder().setMsgContent(message)
                .setTitle("test").addExtra("extra", "extra").build()).build();
    }
}
