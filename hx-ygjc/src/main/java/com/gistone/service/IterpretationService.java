package com.gistone.service;

    import com.gistone.VO.ResultVO;
    import com.gistone.entity.Iterpretation;
    import com.baomidou.mybatisplus.extension.service.IService;
    import com.gistone.util.ResultCp;

    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    * 人类活动解译信息表 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-18
    */
    public interface IterpretationService extends IService<Iterpretation> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,Integer id);

    void delete(List<Integer> id);

    void insert(List<Map<String, Object>> data,Integer imageId,Integer createBy);

    void edit(Iterpretation entity);

        /**
         * 安徽下发任务接口的查询到的斑点的集合
         * @param uid
         * @return
         */
        ResultVO sysSpotData (Integer uid);
    }
