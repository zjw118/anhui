package com.gistone.service;

    import com.gistone.entity.SysLog;
    import com.baomidou.mybatisplus.extension.service.IService;
    import java.util.List;
    import java.util.Map;

    /**
    * <p>
    * 系统日志 服务类
    * </p>
    *
    * @author zf1017@foxmail.com
    * @since 2019-10-31
    */
    public interface SysLogService extends IService<SysLog> {


    Map<String, Object> list(Integer pageNum,Integer pageSize,String Name,String type,String category);

    void delete(List<Integer> id);

    void insert(SysLog entity);

    void edit(SysLog entity);
    }
