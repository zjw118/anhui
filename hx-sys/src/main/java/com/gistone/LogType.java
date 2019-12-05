package com.gistone;

/**
 * @author zf1017@foxmail.com
 * @date 2019/12/3 0003 10:02
 * @description
 */
public enum LogType {

    XTCZ("系统操作",0),
    KJDBGL("红线内置数据",1),


    XMZRHC("项目准入核查",2),
    RLHDYGJC("人类活动遥感监测",3),
    HCRWGL("核查任务管理",4),
    HXXXFW("红线信息服务",5),
    XTGL("系统管理",6);



    private String msg;
    private Integer code;

    LogType(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
