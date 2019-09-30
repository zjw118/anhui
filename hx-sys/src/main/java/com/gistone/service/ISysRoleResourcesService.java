package com.gistone.service;

import com.gistone.entity.SysResources;
import com.gistone.entity.SysRoleResources;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
public interface ISysRoleResourcesService extends IService<SysRoleResources> {

    List<SysResources> selectResourcesByRoleId(List<Integer> roleIds);

}
