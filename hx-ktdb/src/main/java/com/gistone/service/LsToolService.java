package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.LsTool;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

    /**
    * <p>
    *  服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-11-26
    */
    public interface LsToolService extends IService<LsTool> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(LsTool entity, MultipartFile file);

    void edit(LsTool entity, MultipartFile file);
    }
