package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.RlhdGroup;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 人类活动台账信息表 服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-25
 */
public interface RlhdGroupService extends IService<RlhdGroup> {


    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

    void delete(List<Integer> id);

    void insert(String name, Integer createBy, String remark, List<Integer> ids);

    void edit(RlhdGroup entity);

    Map<String,Object> getDetailById(Integer pageNum,Integer pageSize,Integer id);

    void addDataToGroup(Integer groupId, List<Integer> id);

    void deleteDataFromGroup(Integer groupId, List<Integer> ids);
}
