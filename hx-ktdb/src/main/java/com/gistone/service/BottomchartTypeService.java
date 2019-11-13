package com.gistone.service;

    import com.gistone.entity.BottomchartType;
    import com.baomidou.mybatisplus.extension.service.IService;
    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    *  服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-11-13
    */
    public interface BottomchartTypeService extends IService<BottomchartType> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(BottomchartType entity);

    void edit(BottomchartType entity);
    }
