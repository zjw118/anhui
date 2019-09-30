package com.gistone.service.impl;

        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import com.gistone.entity.SysResources;
        import com.gistone.entity.SysRoleResources;
        import com.gistone.mapper.SysRoleResourcesMapper;
        import com.gistone.service.ISysRoleResourcesService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-01-22
 */
@Service
public class SysRoleResourcesServiceImpl extends ServiceImpl<SysRoleResourcesMapper, SysRoleResources> implements ISysRoleResourcesService {
    @Autowired
    private SysRoleResourcesMapper sysRoleResourcesMapper;
    @Override
    public List<SysResources> selectResourcesByRoleId(List<Integer> roleIds) {
        List<SysResources> sysResourcesList = sysRoleResourcesMapper.getResourcesByRoleId(roleIds);
        return sysResourcesList;
    }
}
