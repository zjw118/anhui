package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.LsProjectModel;
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
    public interface LsProjectModelService extends IService<LsProjectModel> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(LsProjectModel entity, MultipartFile file);

    void edit(LsProjectModel entity,MultipartFile file);
    }
