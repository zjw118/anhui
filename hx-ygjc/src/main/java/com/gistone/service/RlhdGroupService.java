package com.gistone.service;

    import com.gistone.entity.Iterpretation;
    import com.gistone.entity.RlhdGroup;
    import com.baomidou.mybatisplus.extension.service.IService;
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


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(String name ,Integer createBy,String remark,List<Integer> ids);

    void edit(RlhdGroup entity);

    List<Iterpretation> getDetailById(Integer id);

    void addDataToGroup(Integer groupId,Integer id);
    }
