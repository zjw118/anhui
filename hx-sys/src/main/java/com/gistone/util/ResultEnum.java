package com.gistone.util;

public enum ResultEnum {
	SUCCESS("0000", "成功"),
    ERROR("9999", "失败"),
    WAIT("1111", "正在处理结果"),
    HANDLEFAIL("1222","处理结果失败"),
    PARAMETEREMPTY("1333","参数不能为空"),
    IDENTIFICATIONEMPTY("1444","接口标识不能为空"),
    INTERFACEEMPTY("1555","接口不存在"),
    INTERFACEERROR("1666","接口错误"),
    NOTLONGIN("2111", "未登陆"),
    INVALIDLONGIN("2222", "登陆失效"),
    USEREXCEPTION("2333", "用户异常");



    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
