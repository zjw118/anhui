package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.St4SysSa;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
public interface St4SysSaMapper extends BaseMapper<St4SysSa> {

    List<Map> listPhoneUserToView(St4SysSa data);

    List<St4SysSa> login(St4SysSa data);

    List<St4SysSa> getUserByidAndCd001(Map data);

    int updateAppUser(St4SysSa data);

    /**
     * 查询出不在核查小组中的人
     * @return
     */
    List<St4SysSa> showUserInCheck();

    IPage<St4SysSa> listUser(Page<St4SysSa> page, @Param("sa") St4SysSa sa);
    List<St4SysSa> listNolimit(St4SysSa sa);


}
