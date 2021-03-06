package com.gistone.service;

    import com.gistone.VO.ResultVO;
    import com.gistone.entity.ShpBatch;
    import com.baomidou.mybatisplus.extension.service.IService;
    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    * 预设数据文件批次表 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-29
    */
    public interface ShpBatchService extends IService<ShpBatch> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name);

    void delete(List<Integer> id);

    void insert(ShpBatch entity);

    void edit(ShpBatch entity);

    void importPreRedlineDate(String url,String remark ,ShpBatch param);

    void importPreMarkerData(String url,String remark);

    void importPreBoardData(String url,String remark);

    List<ShpBatch> getNewList();

    void importPreVector(String url,String remark);

    void importPreImage(String url,String remark);

    void importPreVectorMarker(String url,String remark);

    void importPreImageMarker(String url,String remark);

    ResultVO getShpDetailById(ShpBatch param);
    /**
     * 边界数据列表无分页
     * @param sb
     * @return
     */
    ResultVO listShp (ShpBatch sb);

    }
