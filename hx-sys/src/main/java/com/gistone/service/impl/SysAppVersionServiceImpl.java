package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.entity.SysAppVersion;
import com.gistone.entity.SysMobileUser;
import com.gistone.entity.SysUser;
import com.gistone.mapper.SysAppVersionMapper;
import com.gistone.mapper.SysMobileUserMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISysAppVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ConfigUtils;
import com.gistone.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-16
 */
@Service
@Slf4j
public class SysAppVersionServiceImpl extends ServiceImpl<SysAppVersionMapper, SysAppVersion> implements ISysAppVersionService {
    @Autowired
    private SysAppVersionMapper sysAppVersionMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ConfigUtils configUtils;

    @Override
    public Map<String, Object> getList(Integer pageNum, Integer pageSize) {
        QueryWrapper<SysAppVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 1);
        queryWrapper.orderByDesc("create_date");
        IPage<SysAppVersion> sysAppVersionIPage = sysAppVersionMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        Map<String, Object> result = new HashMap<>();
        if (sysAppVersionIPage.getRecords() != null && sysAppVersionIPage.getRecords().size() > 0) {

        }
        for (SysAppVersion record : sysAppVersionIPage.getRecords()) {
            SysUser sysMobileUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", record.getCreateBy()));
            if (sysMobileUser != null) {
                record.setCreateUser(sysMobileUser.getUsername());
            }
        }
        result.put("rows", sysAppVersionIPage.getRecords());
        result.put("total", sysAppVersionIPage.getTotal());
        return result;
    }

    @Override
    public void delete(Integer id) {
        SysAppVersion sysAppVersion = sysAppVersionMapper.selectById(id);
        sysAppVersion.setDelFlag(false);
        sysAppVersionMapper.updateById(sysAppVersion);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = null;
        try {
            String path = configUtils.getPICTURE_PATH() + "sys/apk/";
            filePath = PictureUtils.getPicturePath(path, file);
        } catch (Exception e) {
            log.error("上传apk异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        }

        return filePath;

    }
}
