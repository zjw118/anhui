package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCba;
import com.gistone.entity.excel.CbaVo;
import com.gistone.mapper.St4ScsCbaMapper;
import com.gistone.service.ISt4ScsCbaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 生态保护红线陆地边界数据表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
@Service
public class St4ScsCbaServiceImpl extends ServiceImpl<St4ScsCbaMapper, St4ScsCba> implements ISt4ScsCbaService {
    @Autowired
    private  St4ScsCbaMapper st4ScsCbaMapper;
   @Autowired
    private ConfigUtils  configUtils;

    @Override
    public ResultVO listRedLineLedger(St4ScsCba cba){
        Page<St4ScsCba> page = new Page<>(cba.getPageNumber(),cba.getPageSize());
        if(ObjectUtils.isNotNullAndEmpty(cba.getEndTime())){
            cba.setEndTime(cba.getEndTime()+" 23:59:59");

        }
        if(ObjectUtils.isNotNullAndEmpty(cba.getStrTime())){
            cba.setStrTime(cba.getStrTime()+" 00:00:00");

        }
        IPage<St4ScsCba> cbaList = st4ScsCbaMapper.listRedLineLedger(page,cba );
        Result result = new Result();
        result.setRows(cbaList.getRecords());
        result.setTotal((int)cbaList.getTotal());
        result.setPage((int)cbaList.getPages());
        return ResultVOUtil.success(result);
    }

    @Override
    public ResultVO exportRedLineBorder(St4ScsCba cba, HttpServletResponse response) {

        List<CbaVo> voList = st4ScsCbaMapper.exportRedLineBorder(cba);
        String filepath = ExcelUtil.toXls("生态保护红线陆地边界数据统计表", voList,
                configUtils.getExcel_PATH(), CbaVo.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }
}
