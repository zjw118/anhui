package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.VO.VersionVO;
import com.gistone.entity.SysAppVersion;
import com.gistone.entity.SysShapeVersion;
import com.gistone.entity.SysUser;
import com.gistone.mapper.SysShapeVersionMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISysShapeVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.ConfigUtils;
import com.gistone.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
@Service
@Slf4j
public class SysShapeVersionServiceImpl extends ServiceImpl<SysShapeVersionMapper, SysShapeVersion> implements ISysShapeVersionService {

    @Autowired
    private SysShapeVersionMapper sysShapeVersionMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ConfigUtils configUtils;

    @Override
    public Map<String, Object> getShapeList(Integer pageNum, Integer pageSize) {
        QueryWrapper<SysShapeVersion> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", 1);
        wrapper.orderByDesc("create_date");
        IPage<SysShapeVersion> sysAppVersionIPage = sysShapeVersionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        if (sysAppVersionIPage.getRecords() != null && sysAppVersionIPage.getRecords().size() > 0) {
            for (SysShapeVersion record : sysAppVersionIPage.getRecords()) {
                SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", record.getCreateBy()));
                record.setCreateUser(sysUser.getUsername());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rows", sysAppVersionIPage.getRecords());
        result.put("total", sysAppVersionIPage.getTotal());
        return result;
    }

    @Override
    public void delete(Integer id) {
        SysShapeVersion sysShapeVersion = sysShapeVersionMapper.selectById(id);
        sysShapeVersion.setDelFlag(0);
        sysShapeVersionMapper.updateById(sysShapeVersion);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = null;
        try {
            String path = configUtils.getPICTURE_PATH() + "sys/shape/";
            filePath = PictureUtils.getPicturePath(path, file);
        } catch (Exception e) {
            log.error("上传apk异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }

    @Override
    public List<VersionVO> updateFile() {
        List<VersionVO> versionVOList = new ArrayList<>();
        //1.界桩
        versionVOList.add(getVersionVO(1, 1));
        //2.标识牌
        versionVOList.add(getVersionVO(2, 2));
        //3.红线
        versionVOList.add(getVersionVO(3,3));
        //4.拐点
        versionVOList.add(getVersionVO(4,4));

        return versionVOList;

    }

    private VersionVO getVersionVO(Integer type,Integer fileName) {
        IPage<SysShapeVersion> sysAppVersionIPage = sysShapeVersionMapper.selectPage(new Page<>(1, 1), new QueryWrapper<SysShapeVersion>().eq("type",type).orderByDesc("id"));
        VersionVO versionVO = new VersionVO();
        if (sysAppVersionIPage.getRecords() != null && sysAppVersionIPage.getRecords().size() > 0) {
            versionVO.setPathUrl(sysAppVersionIPage.getRecords().get(0).getShapeUrl());
            versionVO.setVersion(sysAppVersionIPage.getRecords().get(0).getVersion());
        }
        versionVO.setFileType(fileName);
        return versionVO;
    }
}
