package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.RemoteSensingData;
import com.gistone.mapper.RemoteSensingDataMapper;
import com.gistone.service.IRemoteSensingDataService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2019-12-04
 */
@Service
public class RemoteSensingDataServiceImpl extends ServiceImpl<RemoteSensingDataMapper, RemoteSensingData> implements IRemoteSensingDataService {

    @Autowired
    private RemoteSensingDataMapper remoteSensingDataMapper;

    @Autowired
    private ConfigUtils configUtils;

    @Override
    public Map<String, Object> list(RemoteSensingData param) {
        QueryWrapper<RemoteSensingData> wrapper = new QueryWrapper<>();
        Integer pageNum = (Integer) param.getPageNumber();
        Integer pageSize = (Integer) param.getPageSize();
        String rsdBsm = (String) param.getRsdBsm();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (StringUtils.isNotBlank(rsdBsm)) {
            wrapper.like("rsd_bsm", rsdBsm);
        }
        wrapper.eq("rsd_del_flag", 1);
        IPage<RemoteSensingData> iPage = remoteSensingDataMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());
        return result;
    }

    @Override
    public Map<String, Object> exportExcel(RemoteSensingData param, HttpServletResponse response) {
        QueryWrapper<RemoteSensingData> wrapper = new QueryWrapper<>();

        String rsdBsm = (String) param.getRsdBsm();

        if (StringUtils.isNotBlank(rsdBsm)) {
            wrapper.like("rsd_bsm", rsdBsm);
        }
        wrapper.eq("rsd_del_flag", 1);
        List<RemoteSensingData> list = remoteSensingDataMapper.selectList(wrapper);

        String filepath = ExcelUtil.toXls("遥感影像数据台账", list, configUtils.getExcel_PATH(), RemoteSensingData.class, response);


        Map<String, Object> result = new HashMap<>();

        result.put("excelPath", filepath.substring(2));
        return result;

    }
}
