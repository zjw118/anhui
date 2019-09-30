package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.VersionVO;
import com.gistone.entity.SysDbVersion;
import com.gistone.entity.SysUser;
import com.gistone.mapper.SysDbVersionMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISysDbVersionService;
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
 *  服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
@Service
@Slf4j
public class SysDbVersionServiceImpl extends ServiceImpl<SysDbVersionMapper, SysDbVersion> implements ISysDbVersionService {
        @Autowired
        private SysDbVersionMapper sysDbVersionMapper;
        @Autowired
        private SysUserMapper sysUserMapper;
        @Autowired
        private ConfigUtils configUtils;


    @Override
    public Map<String, Object> getList(Integer pageNum, Integer pageSize) {
        QueryWrapper<SysDbVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 1);
        queryWrapper.orderByDesc("create_date");
        IPage<SysDbVersion> sysDbVersionIPage = sysDbVersionMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        Map<String, Object> result = new HashMap<>();
        if (sysDbVersionIPage.getRecords() != null && sysDbVersionIPage.getRecords().size() > 0) {

        }
        for (SysDbVersion record : sysDbVersionIPage.getRecords()) {
            SysUser sysMobileUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", record.getCreateBy()));
            if (sysMobileUser != null) {
                record.setCreateUser(sysMobileUser.getUsername());
            }
        }
        result.put("rows", sysDbVersionIPage.getRecords());
        result.put("total", sysDbVersionIPage.getTotal());
        return result;
    }

    @Override
    public void delete(Integer id) {
        SysDbVersion sysDbVersion = sysDbVersionMapper.selectById(id);
        sysDbVersion.setDelFlag(0);
        sysDbVersionMapper.updateById(sysDbVersion);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = null;
        try {
            String path = configUtils.getPICTURE_PATH() + "sys/db/";
            filePath = PictureUtils.getPicturePath(path, file);
        } catch (Exception e) {
            log.error("上传apk异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        }

        return filePath;

    }

    @Override
    public VersionVO updateFile() {
        IPage<SysDbVersion> sysDbVersionIPage = sysDbVersionMapper.selectPage(new Page<>(1, 1), new QueryWrapper<SysDbVersion>().orderByDesc("id"));
        VersionVO versionVO = new VersionVO();
        if(sysDbVersionIPage.getRecords()!=null&&sysDbVersionIPage.getRecords().size()>0){
            versionVO.setPathUrl(sysDbVersionIPage.getRecords().get(0).getDbUrl());
            versionVO.setVersion(sysDbVersionIPage.getRecords().get(0).getVersion());
        }
        versionVO.setFileType(5);
        return versionVO;
    }
}
