package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCbd;
import com.gistone.mapper.ISt4ScsCbdMapper;
import com.gistone.service.ISt4ScsCbdService;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 移动端提交检测表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-26
 */
@Service
public class St4ScsCbdServiceImpl extends ServiceImpl<ISt4ScsCbdMapper, St4ScsCbd> implements ISt4ScsCbdService {
    @Autowired
    private ISt4ScsCbdMapper st4ScsCbdMapper;
@Override
        public ResultVO listCheckMsg (){
            QueryWrapper<St4ScsCbd> cbdQueryWrapper = new QueryWrapper<>();
            cbdQueryWrapper.last("limit 0,5");
            cbdQueryWrapper.eq("del_flag",1);
            return ResultVOUtil.success(st4ScsCbdMapper.selectList(cbdQueryWrapper));

        }
}
