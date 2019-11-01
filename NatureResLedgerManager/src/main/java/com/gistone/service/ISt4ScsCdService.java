package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCd;
import com.gistone.entity.St4SysSa;
import com.gistone.util.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题点表 服务类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-13
 */
public interface ISt4ScsCdService extends IService<St4ScsCd> {

    Result listCheckPointToView(St4ScsCd data);
    Result listCheckPoint(St4ScsCd data, St4SysSa seUser);
    Result insertCheckPoint(St4ScsCd cd);
    Result sysPointDataLd (Integer roleId,Integer uid);


//    ResultCp insertSpotDataFromApp (St4ScsCd cd);

    List list2(Integer id);
    Map<String, Object> list(Integer pageNum, Integer pageSize, Integer id);
    void delete(List<Integer> id);
    void insert(List<Map<String, Object>> data,Integer imageId,Integer createBy);
    void edit(St4ScsCd entity);

}
