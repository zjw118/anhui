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

        void edit(Iterpretation entity);
        void insert(List<Map<String, Object>> data,Integer imageId,Integer createBy);
        void delete(List<Integer> ids);
        Map<String, Object> list(Integer pageNum, Integer pageSize,Integer id);
        ResultVO sysSpotData (Integer id);

    }
