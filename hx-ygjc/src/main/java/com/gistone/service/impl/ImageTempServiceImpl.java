package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageTemp;
import com.gistone.mapper.ImageTempMapper;
import com.gistone.service.IImageTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 影像数据表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-24
 */
@Service
public class ImageTempServiceImpl extends ServiceImpl<ImageTempMapper, ImageTemp> implements IImageTempService {
    @Autowired
    private ImageTempMapper imageTempMapper;

    @Override
    public ResultVO listImageTemp(ImageTemp it){
        Page<ImageTemp> page = new Page<>(it.getPageNumber(),it.getPageSize());
        QueryWrapper<ImageTemp> imageTempQueryWrapper = new QueryWrapper<>();
        imageTempQueryWrapper.eq("del_flag", 1);
        imageTempQueryWrapper.orderByDesc("update_date");
        IPage<ImageTemp> list = imageTempMapper.selectPage(page, imageTempQueryWrapper);
        List<ImageTemp> list1 = list.getRecords();
        for (int i = 0;i<list1.size();i++) {
            if(ObjectUtils.isNotNullAndEmpty(list1.get(i).getZipUrl())){
                list1.get(i).setZipUrl(list1.get(i).getZipUrl().substring(list1.get(i).getZipUrl().lastIndexOf("/")+1));
            }
        }
        Result result = new Result();
        result.setRows(list1);
        result.setTotal((int)list.getTotal());
        result.setPage((int)list.getPages());
        return ResultVOUtil.success(result);
    }
}

