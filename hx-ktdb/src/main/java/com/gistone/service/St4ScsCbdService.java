package com.gistone.service;

    import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCbd;

import java.util.Map;

    /**
    * <p>
    * 移动端提交检测表 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-12-04
    */
    public interface St4ScsCbdService extends IService<St4ScsCbd> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name,String time,String title,String content);

    void delete(Integer id);

    void insert(St4ScsCbd entity);

    void edit(St4ScsCbd entity);
    }
