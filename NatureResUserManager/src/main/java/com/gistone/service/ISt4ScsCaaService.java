package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCaa;
import com.gistone.util.ResultCp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-27
 */
public interface ISt4ScsCaaService extends IService<St4ScsCaa> {

    ResultCp getNewVersion(St4ScsCaa data);
}
