package com.gistone.service;

import com.gistone.entity.St4ScsCad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.util.Result;

/**
 * <p>
 * 消息列表主键 服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-27
 */
public interface ISt4ScsCadService extends IService<St4ScsCad> {

    Result getMessageData(St4ScsCad cad);


    Result  setMessageRead(St4ScsCad cad);

}
