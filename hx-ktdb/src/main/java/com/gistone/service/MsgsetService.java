package com.gistone.service;

    import com.gistone.entity.Msgset;
    import com.baomidou.mybatisplus.extension.service.IService;
    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    *  服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-12-05
    */
    public interface MsgsetService extends IService<Msgset> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(Msgset entity);

    void edit(Msgset entity);
    }
