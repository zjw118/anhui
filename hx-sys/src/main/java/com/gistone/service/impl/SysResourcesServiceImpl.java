package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.SysResources;
import com.gistone.mapper.SysResourcesMapper;
import com.gistone.service.ISysResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@Service
public class SysResourcesServiceImpl extends ServiceImpl<SysResourcesMapper, SysResources> implements ISysResourcesService {

    @Autowired
    private SysResourcesMapper sysResourcesMapper;
    @Override
    public Integer getMaxSort() {
        Integer sort = sysResourcesMapper.getMaxSort();
        return sort;
    }

    @Override
    public Integer selectMaxSort(Integer id) {
        Integer sort = sysResourcesMapper.selectMaxSort(id);
        return sort;
    }
}
