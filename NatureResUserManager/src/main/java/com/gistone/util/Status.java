package com.gistone.util;

/**
 * Created by wangfan on 2017/5/19.
 */
public class Status {
    
    public static  final  int SUCCESS = 0;//成功

    public static  final  int ERROR =1;//失败

    public static final int  MSG = 2;

    public static final int  NOUSER = 3;

    public static final int  REPEAT = 4;
    public static final int  CONFIRM = 5;

    public static final int  ATTENDANCE = 7;
    
    public static final int  NmaeRepeat_ERROR = 10; //字段重复
    public static final int  NmaeRepeat_ERRORS = 9; //字段重复
    
    public static final int Format_ERRORS = 15; //上传格式错误
    


    public static final int SYSTEM_EXCEPTION = 1001;//系统异常
    public static final int USER_CHECK = 1002;//用户验证失败
    public static final int PARAMS_ERROR = 1003;//参数错误
    public static final int DATA_ERROR = 1005;//参数错误
    public static final int ISNULL = 1006;//参数为空
    public static final int SAVE_SUCCESS = 100;//保存成功
    public static final int  PASSWORD_ERROR = 101; //密码错误
    public static final int  USERNAME_ERROR = 102; //账户不存在
    public static final int CHECK_FEILE = 103;//保存成功

    public static final int FILE_EXISTED = 10;
    public static final int FILE_UNEXISTED = 11;
    public static final int FILE_EMPTY = 12;
    public static final int FILE_FOMORT_ERROR = 13;
    
    public static final int FILEPATH_ERROR = 14; //文件不存在
    




}
