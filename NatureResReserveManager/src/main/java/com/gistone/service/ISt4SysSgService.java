package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.St4SysSg;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-14
 */
public interface ISt4SysSgService extends IService<St4SysSg> {

    //Result list(St4SysSg data, St4SysSa seUser);

    Result listByParam(St4SysSg data);

    Result listTreeReserveData(St4SysSg data);

    Result insert(St4SysSg data);

    Result update(St4SysSg data);

    Result delete(St4SysSg data);

    ResultCp appList(St4SysSg data);

    ResultCp appListToPoint(St4SysSg data);

    Result getDataById(St4SysSg data, HttpServletRequest request) throws IOException;
    //查询出当前人员已授权的保护地边界
    Result getReserveDataBySaid(Integer said);


}
