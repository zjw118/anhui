package com.gistone.mapper;

import com.gistone.entity.St4ScsCz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.St4SysSa;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 问题点分组表 Mapper 接口
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
public interface St4ScsCzMapper extends BaseMapper<St4ScsCz> {
    List<St4ScsCz> listGroup(St4ScsCz cz);

    List<St4SysSa> getGroupUserDetail(St4ScsCz cz);

    St4ScsCz getRecentTask(Integer uid);

    St4ScsCz getUserShare(@Param("sa001") Integer sa001,@Param("cl001") Integer cl001);

}
