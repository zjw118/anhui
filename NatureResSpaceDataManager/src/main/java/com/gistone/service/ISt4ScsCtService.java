package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.entity.St4ScsCt;
import com.gistone.util.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
public interface ISt4ScsCtService extends IService<St4ScsCt> {


    /**
     * @explain : 添加空间数据信息，判断数据类型ct007，添加子数据或数据分类
     * @author xxh
     * @date 2019/8/12
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result insertSpaceData(St4ScsCt st4ScsCt);

    /**
     * @explain : 修改空间数据信息，判断是否有ct001主键
     * @author xxh
     * @date 2019/8/12
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result updateSpaceData(St4ScsCt st4ScsCt);

    /**
     * @explain : 删除空间数据信息
     * @author xxh
     * @date 2019/8/12
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result deleteSpaceData(St4ScsCt st4ScsCt);

    /**
     * @explain : 根据分页参数及字段查询参数返回对应的分页结果
     * @author xxh
     * @date 2019/8/12
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result listSpaceDataByPage(St4ScsCt st4ScsCt);

    /**
     * @explain : 根据字段查询参数返回对应的树型结构结果
     * @author xxh
     * @date 2019/8/12
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result listSpaceDataTree(St4ScsCt st4ScsCt);

    /**
     * @explain : 根据字段参数查询所有的数据分类信息，返回主键及对应的名称
     * @author xxh
     * @date 2019/8/13
     * @param st4ScsCt
     * @return com.gistone.util.Result
     */
    public Result listSpaceDataType(St4ScsCt st4ScsCt);


}
