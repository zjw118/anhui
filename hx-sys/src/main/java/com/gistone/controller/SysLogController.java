package com.gistone.controller;


import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.gistone.VO.ResultVO;
import com.gistone.entity.SysLog;
import com.gistone.service.SysLogService;
import com.gistone.util.ConfigUtils;
import com.gistone.util.ExcelUtil;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-31
 */

@RestController
@RequestMapping("/api/sys/sysLog")
public class SysLogController {
    @Autowired
    private SysLogService service;

    @Autowired
    private ConfigUtils configUtils;

    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    @Value("${WORD_PATH}")
    private String WORD_PATH;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }


        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("title");
        String type = (String) params.get("type");
        String category = (String) params.get("category");
        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = service.list(pageNum, pageSize, name, type, category);
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        SysLog entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody SysLog entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        //判断添加人是否为空
        service.insert(entity);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> params) {
        List<Integer> id = (List<Integer>) params.get("id");
        if (id != null && id.size() > 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody SysLog entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
//判断更新人加人是否为空
        service.edit(entity);
        return ResultVOUtil.success();
    }

    @PostMapping(value = "/exportExcel")
    public ResultVO exoprtExcel(HttpServletResponse response) {
        List<SysLog> list = service.list(null);

        String filepath = ExcelUtil.toXls("系统操作日志", list, configUtils.getExcel_PATH(), SysLog.class, response);
        Map map1 = new HashMap();
        map1.put("filepath", filepath.substring(2));
        return ResultVOUtil.success(map1);
    }

    @PostMapping("/getTotal")
    public ResultVO getTotal() {
        Map<String, Object> result = service.getTotal();

        return ResultVOUtil.success(result);
    }

    @PostMapping("exportLogReport")
    public ResultVO exportLogReport(@RequestBody Map<String, Object> params) {


        String images = (String) params.get("image");
        if (StringUtils.isBlank(images)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "缩略图不能为空");
        }

        String proportion = (String) params.get("proportion");
        if (StringUtils.isBlank(proportion)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "图片比例不能为空");
        }
        int i = 0;
        if (images.contains(",")) {
            i = images.indexOf(",");
        }
        String str = "";
        if (images.length() > 0) {
            str = images.substring(i + 1);
        }

        byte[] bytes1 = new byte[0];
        try {
            bytes1 = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageEntity image = new ImageEntity();
        double d = Double.parseDouble(proportion) * 500;
        int height = (int) d;
        image.setHeight(height);
        image.setWidth(500);
        image.setData(bytes1);
        image.setType(ImageEntity.Data);
        Map<String,Object> param = new HashMap<>();
        param.put("image", image);

        //导出word文档
        //导出word并把路径放到projectAdmission对象属性中
        String lastName = "";
        try {


            XWPFDocument doc = WordExportUtil.exportWord07(
                    "word/logReport.docx", param);
            String fileName = "日志统计分析报告";
            lastName = WORD_PATH + fileName + ".docx";
            FileOutputStream fos = new FileOutputStream(lastName);
            doc.write(fos);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVOUtil.success(lastName.substring(2));

    }


}
