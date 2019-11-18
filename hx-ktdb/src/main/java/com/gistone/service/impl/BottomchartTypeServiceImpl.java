package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.BottomchartType;
import com.gistone.mapper.BottomchartTypeMapper;
import com.gistone.service.BottomchartTypeService;
import com.gistone.util.TypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-11-13
 */
@Service
@Transactional
@Slf4j
public class BottomchartTypeServiceImpl extends ServiceImpl<BottomchartTypeMapper, BottomchartType> implements BottomchartTypeService {

    @Autowired
    private BottomchartTypeMapper mapper;






    @Override
    public List<BottomchartType> list() {
        List<BottomchartType> bottomchartTypes = mapper.selectList(null);
        List<BottomchartType> bottomchartTypes1 = TypeUtil.buildTree(bottomchartTypes, 0);
        return bottomchartTypes1;
    }

    @Override
    public void delete(List<Integer> ids) {

        for (Integer id : ids) {
            mapper.deleteById(id);
        }

    }

    @Override
    public void insert(BottomchartType entity) {
        mapper.insert(entity);

    }


    @Override
    public void edit(BottomchartType entity) {
        mapper.updateById(entity);

    }

}


