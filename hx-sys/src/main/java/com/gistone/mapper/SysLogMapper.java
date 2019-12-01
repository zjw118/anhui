package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-31
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    List<Map<String, Object>> getTotal(@Param("currentTime") LocalDate currentTime, @Param("beforeTime") LocalDate beforeTime);

   int  getBeforeCount( @Param("beforeTime") LocalDate beforeTime);
}
