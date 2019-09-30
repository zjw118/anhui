package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.VO.VersionVO;
import com.gistone.entity.SysDbVersion;
import com.gistone.service.ISysDbVersionService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/api/sys/sysDbVersion")
public class SysDbVersionController {
    @Autowired
    private ISysDbVersionService sysDbVersionService;

    @PostMapping(value = "/list")
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

        Map<String, Object> result = sysDbVersionService.getList(pageNum, pageSize);
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
        sysDbVersionService.delete(id);

        return ResultVOUtil.success();
    }

    @PostMapping(value = "/add")
    public ResultVO add(@RequestParam("version") String version, String remark, @RequestParam("createBy") Integer createBy, @RequestParam("file") MultipartFile file) {
        SysDbVersion sysDbVersion = new SysDbVersion();
        if (StringUtils.isBlank(version)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "版本不能为空");
        }
        sysDbVersion.setVersion(version);
        if (createBy == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }
        sysDbVersion.setCreateBy(createBy);
        if (file == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "apk不能为空");
        }

        if (StringUtils.isNotBlank(remark)) {
            sysDbVersion.setRemark(remark);
        }
        //保存apk返回地址
        String path = sysDbVersionService.uploadFile(file);
        //去掉盘符前缀
        String rePath = null;
        if (path.length() > 3) {
            rePath = path.substring(2);
        }
        sysDbVersion.setDbUrl(rePath);
        sysDbVersion.setCreateDate(new Date());

        sysDbVersionService.save(sysDbVersion);





        return ResultVOUtil.success();
    }

    @RequestMapping(value = "/updateFile")
    public VersionVO updateFile(){

        return sysDbVersionService.updateFile();
    }


}

