package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.VO.VersionVO;
import com.gistone.entity.SysShapeVersion;
import com.gistone.service.ISysShapeVersionService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/api/sys/sysShapeVersion")
public class SysShapeVersionController {

    @Autowired
    private ISysShapeVersionService sysShapeVersionService;

    /**
     * @return
     * @description: shape文件列表
     * @params
     * @author zf1017@foxmail.com
     * @date 2019/5/21 0021 17:01
     */
    @RequestMapping(value = "/list")
    public ResultVO list(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = sysShapeVersionService.getShapeList(pageNum, pageSize);
        return ResultVOUtil.success(result);
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        sysShapeVersionService.delete(id);

        return ResultVOUtil.success();
    }

    @PostMapping(value = "/add")
    public ResultVO add(@RequestParam("version") String version, String remark, @RequestParam("createBy") Integer createBy, @RequestParam("type") Integer type, @RequestParam("file") MultipartFile file) {
        SysShapeVersion sysShapeVersion = new SysShapeVersion();
        if (StringUtils.isBlank(version)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "版本不能为空");
        }

        SysShapeVersion version1 = sysShapeVersionService.getOne(new QueryWrapper<SysShapeVersion>().eq("version", version));
        if(version1!=null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"版本号已存在");
        }
        sysShapeVersion.setVersion(version);
        if (createBy == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }
        sysShapeVersion.setCreateBy(createBy);
        if (file == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "shape文件不能为空");
        }

        if (type == null || type <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "类型不能为空");
        }
        sysShapeVersion.setType(type);

        if (StringUtils.isNotBlank(remark)) {
            sysShapeVersion.setRemark(remark);
        }

        String filePath = sysShapeVersionService.uploadFile(file);
        //去掉盘符前缀
        String rePath = null;
        if (filePath.length() > 3) {
            rePath = filePath.substring(2);
        }
        sysShapeVersion.setShapeUrl(rePath);
        sysShapeVersion.setCreateDate(new Date());
        sysShapeVersionService.save(sysShapeVersion);

        return ResultVOUtil.success();
    }

    @RequestMapping(value = "/updateFile")
    public List<VersionVO> updateFile() {

        List<VersionVO> versionVOS = sysShapeVersionService.updateFile();
        return versionVOS;
    }

}

