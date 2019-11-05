package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.LoginLog;
import com.gistone.mapper.LoginLogMapper;
import com.gistone.service.ILoginLogService;
import com.gistone.util.PageBean;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2019-04-03
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public ResultVO list(PageBean pageBean) {
        pageBean.setPoSum(loginLogMapper.getPoSum(pageBean));
        pageBean.setPoList(loginLogMapper.selectPoList(pageBean));
        return ResultVOUtil.success(pageBean);
    }



}
