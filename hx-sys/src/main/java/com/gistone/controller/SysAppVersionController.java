package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysAppVersion;
import com.gistone.service.ISysAppVersionService;
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
 * 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/api/sys/sysAppVersion")
public class SysAppVersionController {
    @Autowired
    private ISysAppVersionService sysAppVersionService;

    /**
     * @return
     * @description: 获取版本列表
     * @params
     * @author zf1017@foxmail.com
     * @date 2019/5/16 0016 17:20
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

        Map<String, Object> result = sysAppVersionService.getList(pageNum, pageSize);
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
        sysAppVersionService.delete(id);

        return ResultVOUtil.success();
    }

    @PostMapping(value = "/add")
    public ResultVO add(@RequestParam("version") String version, String remark, @RequestParam("createBy") Integer createBy, @RequestParam("apk") MultipartFile apk) {
        SysAppVersion sysAppVersion = new SysAppVersion();
        if (StringUtils.isBlank(version)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "版本不能为空");
        }
        sysAppVersion.setVersion(version);
        if (createBy == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }
        sysAppVersion.setCreateBy(createBy);
        if (apk == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "apk不能为空");
        }

        if (StringUtils.isNotBlank(remark)) {
            sysAppVersion.setRemark(remark);
        }
        //保存apk返回地址
        String path = sysAppVersionService.uploadFile(apk);
        //去掉盘符前缀
        String rePath = null;
        if (path.length() > 3) {
            rePath = path.substring(2);
        }
        sysAppVersion.setSftPath(rePath);
        sysAppVersion.setCreateDate(new Date());

        sysAppVersionService.save(sysAppVersion);

        return ResultVOUtil.success();
    }


    /**
     * 手机端获取最新版本号
     *
     * @return
     */
    @RequestMapping(value = "/getNewEdition")
    public ResultVO getNewEdition() {
        QueryWrapper<SysAppVersion> sysAppVersionQueryWrapper = new QueryWrapper<>();
        sysAppVersionQueryWrapper.eq("del_flag",1);
        sysAppVersionQueryWrapper.orderByDesc("create_date");
        SysAppVersion sysAppVersion = sysAppVersionService.getOne(sysAppVersionQueryWrapper);
        return ResultVOUtil.success(sysAppVersion);
    }
}

