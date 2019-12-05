package com.gistone.service.impl;

import com.gistone.VO.ResultVO;
import com.gistone.entity.LsRedlineinfo;
import com.gistone.entity.LsRedlineinfoProcess;
import com.gistone.entity.LsRedlineinfoTemplate;
import com.gistone.entity.LsRedlineinfoVersion;
import com.gistone.mapper.LsRedlineinfoMapper;
import com.gistone.service.LsRedlineinfoService;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LsRedlineinfoServiceImpl implements LsRedlineinfoService {
    @Autowired
    private LsRedlineinfoMapper lsRedlineinfoMapper;


    @Override
    public ResultVO processInsert(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processInsert(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO processDelete(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processDelete(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO processUpdate(LsRedlineinfoProcess lsRedlineinfoProcess) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.processUpdate(lsRedlineinfoProcess)));
    }

    @Override
    public ResultVO templateInsert(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateInsert(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO templateDelete(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateDelete(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO templateUpdate(LsRedlineinfoTemplate lsRedlineinfoTemplate) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.templateUpdate(lsRedlineinfoTemplate)));
    }

    @Override
    public ResultVO versionInsert(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionInsert(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO versionDelete(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionDelete(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO versionUpdate(LsRedlineinfoVersion lsRedlineinfoVersion) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.versionUpdate(lsRedlineinfoVersion)));
    }

    @Override
    public ResultVO infoInsert(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoInsert(lsRedlineinfo)));
    }

    @Override
    public ResultVO infoDelete(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoDelete(lsRedlineinfo)));
    }

    @Override
    public ResultVO infoUpdate(LsRedlineinfo lsRedlineinfo) throws Exception {
        return ResultVOUtil.success((lsRedlineinfoMapper.infoUpdate(lsRedlineinfo)));
    }

    @Override
    public ResultVO audit(LsRedlineinfo lsRedlineinfo) throws Exception {




        return null;
    }
}
