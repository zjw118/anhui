package com.gistone.service;

import com.gistone.entity.SysAppVersion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-16
 */
public interface ISysAppVersionService extends IService<SysAppVersion> {
    Map<String,Object> getList(Integer pageNum,Integer pageSize);

    void delete(Integer id);

    String uploadFile(MultipartFile file);

}
