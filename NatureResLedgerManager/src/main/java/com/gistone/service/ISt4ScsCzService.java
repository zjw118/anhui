package com.gistone.service;

import com.gistone.entity.St4ScsCz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4SysSa;
import com.gistone.swagger.St4ScsCzSwagger;
import com.gistone.util.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 问题点分组表 服务类
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
public interface ISt4ScsCzService extends IService<St4ScsCz> {
    /**
     * 巡查人员小组列表
     * @param cz
     * @return
     */
    Result listGroup(St4ScsCz cz, St4SysSa seUser);

    /**
     * 分组详情信息
     * @param cz
     * @return
     */
    Result getGroupUserDetail(St4ScsCz cz);

    /**
     * 修改分组信息包括小组内的人员
     * @param cz
     * @return
     */

    Result updateGroupData(St4ScsCzSwagger cz);

    /**
     * 新增核查小组
     * @param cz
     * @return
     */
    Result insertGroupData(St4ScsCzSwagger cz, HttpServletRequest request);

    /**
     * 展示不在核查小组中用户
     * @return
     */
    Result showUserInCheck();

}
