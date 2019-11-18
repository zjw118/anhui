package com.gistone.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4ScsCba;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 生态保护红线陆地边界数据表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-11-16
 */
public interface St4ScsCbaMapper extends BaseMapper<St4ScsCba> {
    IPage<St4ScsCba> listRedLineLedger(Page<St4ScsCba> page,@Param("cba") St4ScsCba cba);
}
