package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.BottomchartType;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-13
 */
public interface BottomchartTypeService extends IService<BottomchartType> {


    List<BottomchartType> list();

    void delete(List<Integer> id);

    void insert(BottomchartType entity);

    void edit(BottomchartType entity);
}
