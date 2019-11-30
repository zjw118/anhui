package com.gistone.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.DirTemp;
import com.gistone.mapper.DirTempMapper;
import com.gistone.service.IDirTempService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临时文件夹 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-11-27
 */
@Service
public class DirTempServiceImpl extends ServiceImpl<DirTempMapper, DirTemp> implements IDirTempService {

}
