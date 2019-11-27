package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCl;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务批次表 服务类
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
public interface ISt4ScsClService extends IService<St4ScsCl> {

    //Result listForView(St4ScsCl data);
    ResultVO listTask(St4ScsCl data, SysUser seUser);

    ResultVO listCdByTask(St4ScsCl data);

    /**
     * 插入任务
     * @param data
     * @param seUser
     * @return
     */
    ResultVO insertTask(St4ScsCl data, SysUser seUser);
    /**
     * 修改任务
     * @param data
     * @param seUser
     * @return
     */
    ResultVO updateTask(St4ScsCl data, SysUser seUser);
    /**
     * 删除任务
     * @param data
     * @param seUser
     * @return
     */
    ResultVO deleteTask(St4ScsCl data, SysUser seUser);

    /**
     * 任务详情
     * @param data
     * @return
     */
    ResultVO getTaskDetail(St4ScsCl data);

    /**
     * 任务表导入
     * @param items
     * @param seUser
     * @return
     */
    ResultVO importTask(Map<String, MultipartFile> items, St4SysSa seUser);
    ResultVO readExcel(String path) throws Exception;

    /**
     * 任务表导出
     * @return
     */
    ResultVO exportTask(St4ScsCl data);

}
