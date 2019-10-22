package com.gistone.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Description: 极光推送工具类
 * @author zjw
 * @date 2019年8月15日 下午2:50:21
 *
 */
public class JPushUtil {

    private static final Logger log = LoggerFactory.getLogger(JPushUtil.class);

    //masterSecret和appKey在极光推送官网后台管理获取
    private static String masterSecret = "bd765a179f5aefb97a7bbac7";
    private static String appKey = "d1b1b5320aaed3c2bd57a4fb";

    /**
     * 极光推送
     *
     * @param alias 别名
     * @param alert	推送的消息内容
     * @param flag 操作类型标识 1：下发参考点 ; 2台账超时未整改或填报时限仅剩5天 3重新激活 4审诉审核未通过
     */
    public static boolean jiGuangPush(String alias, String alert, String flag) {

        // 声明别名
        log.info("对别名" + alias + "的用户推送信息!");

        PushResult result = push(String.valueOf(alias), alert, flag);

        if (result != null && result.isResultOK()) {
            log.info("针对别名" + alias + "的信息推送成功！");
            return true;
        } else {
            log.info("针对别名" + alias + "的信息推送失败！");
            return false;
        }

    }

    /**
     * 极光推送方法（采用java SDK）
     *
     * @param alias 别名
     * @param alert 推送的内容
     * @param flag 操作类型标识 1：下发参考点; 2台账超时未整改
     * @return PushResult
     */
    private static PushResult push(String alias, String alert, String flag) {

        try {
            //创建JPushClient实例
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null,
                    ClientConfig.getInstance());

            // For push, all you need do is to build PushPayload object.
            PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert, flag);

            //推送 并返回推送结果
            return jpushClient.sendPush(payload);

        } catch (APIConnectionException e) {

            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {

            log.error("Error response from JPush server. Should review and fix it. ",
                    e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias 别名
     * @param alert	推送的消息内容
     * @param flag 操作类型标识 1：下发参考点; 2台账超时未整改
     * @return PushPayload
     */
    private static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert, String flag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios()) //设置推送的平台
                .setAudience(Audience.alias(alias))	 //设置接收信息的对象（通过别名（用户唯一标识））
                .setNotification(Notification.newBuilder()  //设置通知
                        .addPlatformNotification(
                                AndroidNotification.newBuilder()	//android
                                        .addExtra("type", "infomation")
                                        .addExtra("flag", flag)
                                        .setAlert(alert).build())	//设置通知的内容 alert

                        .addPlatformNotification(
                                IosNotification.newBuilder().setSound("default")		//ios
                                        .addExtra("type", "infomation")
                                        .addExtra("flag", flag)
                                        .setAlert(alert).build())   //设置通知的内容 alert
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(true) // true-推送生产环境， false-推送开发环境（测试使用参数）
                        .setTimeToLive(90) // 消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }
}
