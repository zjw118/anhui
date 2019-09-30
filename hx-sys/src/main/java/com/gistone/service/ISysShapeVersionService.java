package com.gistone.service;

import com.gistone.VO.VersionVO;
import com.gistone.entity.SysShapeVersion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
public interface ISysShapeVersionService extends IService<SysShapeVersion> {
    Map<String,Object> getShapeList(Integer pageNum,Integer pageSize);

    void delete(Integer id);

    String uploadFile(MultipartFile file);

    List<VersionVO> updateFile();




}
