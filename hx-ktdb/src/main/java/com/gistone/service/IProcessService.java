package com.gistone.service;

import com.gistone.entity.LsTool;
import com.gistone.entity.Process;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-27
 */
public interface IProcessService extends IService<Process> {

    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

}
