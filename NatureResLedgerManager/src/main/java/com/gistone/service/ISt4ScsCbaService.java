package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCba;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 生态保护红线陆地边界数据表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
public interface ISt4ScsCbaService extends IService<St4ScsCba> {
    ResultVO listRedLineLedger(St4ScsCba cba);

    /**
     * 导出生态保护红线陆地边界数据Excel
     * @param cba
     * @param response
     * @return
     */
    ResultVO exportRedLineBorder(St4ScsCba cba , HttpServletResponse response);
}
