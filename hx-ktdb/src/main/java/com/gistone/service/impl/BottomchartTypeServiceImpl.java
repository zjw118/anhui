package com.gistone.service.impl;

    import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.gistone.entity.BottomchartType;
    import com.gistone.mapper.BottomchartTypeMapper;
    import com.gistone.service.BottomchartTypeService;
    import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import javax.transaction.Transactional;
    import org.apache.commons.lang3.StringUtils;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

/**
    * <p>
    *  服务实现类
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
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {

    QueryWrapper<BottomchartType> wrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(userName)){
    //wrapper.likeRight("SA008",userName);
    }
    // wrapper.eq("SA007",1);
    //wrapper.orderByDesc("SA003");
    IPage<BottomchartType> iPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);


    Map<String, Object> result = new HashMap<>();
    result.put("rows", iPage.getRecords());
    result.put("total", iPage.getTotal());

    return result;
}

    @Override
    public void delete(List<Integer> ids) {
    //具体逻辑

        }

    @Override
    public void insert(BottomchartType entity) {
    //具体逻辑

    }



    @Override
    public void edit(BottomchartType entity) {
        //具体逻辑
    }

    }


