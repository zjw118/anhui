package com.gistone.service;

import com.gistone.entity.SysResources;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface ISysResourcesService extends IService<SysResources> {
    Integer getMaxSort();
    Integer selectMaxSort(Integer id);

}
