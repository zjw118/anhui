package com.gistone.service;

    import com.gistone.entity.LsKnowledge;
    import com.baomidou.mybatisplus.extension.service.IService;
    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    *  服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-11-26
    */
    public interface LsKnowledgeService extends IService<LsKnowledge> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(LsKnowledge entity);

    void edit(LsKnowledge entity);
    }
