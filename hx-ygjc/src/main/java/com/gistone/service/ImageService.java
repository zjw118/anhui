package com.gistone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.util.ResultVOUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
* <p>
* 影像数据表 服务类
* </p>
*
* @author zf1017@foxmail.com
* @since 2019-10-18
*/
public interface ImageService extends IService<Image> {
    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

    void delete(List<Integer> id);

    void insert(String name, String url, Integer createBy,String remark);

    void edit(Integer id,String name,String url,Integer updateBy,String remark);

    List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime);

    int getBeforeCount(String code,LocalDate beforeTime);

    List<Map<String, Object>> getRlhdTotal();

    ResultVO getAudit(Integer id);

    ResultVO audit(Image image);




}

