package com.gistone.controller;


import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.CoordinateVO;
import com.gistone.VO.ResultVO;
import com.gistone.entity.AnalysisReport;
import com.gistone.entity.ProjectAdmission;
import com.gistone.exception.ProjectException;
import com.gistone.service.IAnalysisReportService;
import com.gistone.service.IProjectAdmissionService;
import com.gistone.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 项目准入前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/api/ktdb/projectAdmission")
@Slf4j
@Transactional
public class ProjectAdmissionController {
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    @Autowired
    private IProjectAdmissionService projectAdmissionService;

    @Autowired
    private IAnalysisReportService analysisReportService;
    @Value("${WORD_PATH}")
    private String WORD_PATH;

    /**
     * @return
     * @description: 获取项目准入列表
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/5 0005 14:50
     */
    @PostMapping(value = "/list")
    public ResultVO getProjectList(@RequestBody Map<String, Object> paramsMap) {
        //项目名称、位置形状、创建时间范围
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");

        String projectName = (String) dataParam.get("projectName");
        String shape = (String) dataParam.get("shape");

        String startTime = (String) dataParam.get("startTime");
        String endTime = (String) dataParam.get("endTime");

        String type = (String) dataParam.get("type");
        String attribute = (String) dataParam.get("attribute");
        String time = (String)dataParam.get("time");


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = projectAdmissionService.getProjectList(pageNum, pageSize, projectName, shape, startTime, endTime,type,attribute,time);

        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 删除项目准入
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/5 0005 14:50
     */
    @PostMapping(value = "/delete")
    public ResultVO deleteById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        projectAdmissionService.deleteById(id);
        return ResultVOUtil.success();
    }

    /**
     * @return
     * @description: 查看单个项目
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/8 0008 15:43
     */
    @PostMapping(value = "/getProjectById")
    public ResultVO getProjectById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }

        // 还需要展示的有坐标和分析报告
        ProjectAdmission projectAdmission = projectAdmissionService.getById(id);
        List<AnalysisReport> analysisReports = analysisReportService.list(new QueryWrapper<AnalysisReport>().eq("project_id", id));
        projectAdmission.setAnalysisReports(analysisReports);

        String coordinate = projectAdmission.getCoordinateUrl();

        List<Object> objects = JSON.parseArray(coordinate, Object.class);

        projectAdmission.setCoordinate(objects);
        projectAdmission.setCoordinateUrl("");

        return ResultVOUtil.success(projectAdmission);
    }

    /**
     * @return
     * @description: 项目增加
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/8 0008 15:44
     */
    @PostMapping(value = "/add")
    public ResultVO addProject(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Map<String, Object> params = new HashMap<>();
        ProjectAdmission projectAdmission = new ProjectAdmission();
        String projectName = (String) dataParam.get("projectName");
        if (StringUtils.isBlank(projectName)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "项目名称不能为空");
        }
        projectAdmission.setName(projectName);
        params.put("name", projectName);

        String type = (String) dataParam.get("type");
        if(StringUtils.isBlank(type)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"类型不能为空");
        }
        projectAdmission.setType(type);

        String attribute = (String) dataParam.get("attribute");
        if(StringUtils.isBlank(attribute)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"属性不能为空");
        }
        projectAdmission.setAttribute(attribute);
        String time = (String) dataParam.get("time");
        if(StringUtils.isBlank(time)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"时间不能为空");
        }
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date2 = format2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        projectAdmission.setTime(date2);


        String shape = (String) dataParam.get("shape");

        if (StringUtils.isBlank(shape)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "位置形状不能为空");
        }
        projectAdmission.setShape(shape);

        Integer radius = (Integer) dataParam.get("radius");
        if (radius == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "缓冲半径不能为空");
        }
        projectAdmission.setRadius(radius);
        params.put("radius", radius);

        String result = (String) dataParam.get("result");
        if (StringUtils.isBlank(result)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "分析结果不能为空");
        }
        projectAdmission.setResult(result);
        params.put("result", result);

        Integer createBy = (Integer) dataParam.get("createBy");
        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人id不能为空");
        }

        String description = (String) dataParam.get("description");
        if (StringUtils.isNotBlank(description)) {
            projectAdmission.setDescription(description);
        }

        String bufferRange = (String) dataParam.get("bufferRange");
        projectAdmission.setBufferRange(Double.parseDouble(bufferRange));
        projectAdmission.setCreateDate(new Date());
        params.put("bufferRange", bufferRange);

        String images = (String) dataParam.get("image");
        if(StringUtils.isBlank(images)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"缩略图不能为空");
        }

        String proportion = (String) dataParam.get("proportion");
         if(StringUtils.isBlank(proportion)){
             return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"图片比例不能为空");
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
        double d = Double.parseDouble(proportion)*500;
        int height = (int) d;
        image.setHeight(height);
        image.setWidth(500);
        image.setData(bytes1);
        image.setType(ImageEntity.Data);

        params.put("image", image);

        List<Object> coordinate = (List<Object>) dataParam.get("coordinate");
        if (coordinate != null && coordinate.size() > 0) {


            String jsonStr = JSON.toJSONString(coordinate);

            projectAdmission.setCoordinateUrl(jsonStr);
        }

        Integer id = (Integer) dataParam.get("id");

        List<Object> detail = (List<Object>) dataParam.get("detail");
        List<AnalysisReport> analysisReports = new ArrayList<>();
        //准备分析报考的参数
        if (detail != null && detail.size() > 0) {

            for (Object o : detail) {
                Map<String, Object> detailInfo = (Map<String, Object>) o;
                AnalysisReport analysisReport = new AnalysisReport();
                analysisReport.setProjectId(projectAdmission.getId());

//                Integer redlineId = (Integer) detailInfo.get("redineId");
                /*if (redlineId == null || redlineId <= 0) {
                    throw new ProjectException(ResultEnum.REDLINEID_EMPTY);
                }*/
                analysisReport.setRedlineId(1111);

                String intersectArea = (String) detailInfo.get("intersectArea");
                if (intersectArea == null) {
                    throw new ProjectException(ResultEnum.INTERSECTAREA_EMPTY);
                }
                analysisReport.setIntersectArea(Double.parseDouble(intersectArea));

                analysisReports.add(analysisReport);

                String area = new BigDecimal(analysisReport.getIntersectArea().toString()).toString();
                analysisReport.setArea(area);
            }
        }

        params.put("list", analysisReports);

        //导出word并把路径放到projectAdmission对象属性中
        try {
            if (analysisReports.size() > 0) {
                XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/export.docx", params);
                String fileName = projectName + "分析报告";
                String lastName = WORD_PATH + fileName + ".docx";
                projectAdmission.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);
                doc.write(fos);
                fos.close();
            } else {
                XWPFDocument doc = WordExportUtil.exportWord07(
                        "word/export1.docx", params);
                String fileName = projectName + "分析报告";
                String lastName = WORD_PATH + fileName + KeyUtil.genUniqueKey() + ".docx";
                projectAdmission.setFileUrl(lastName.substring(2));
                FileOutputStream fos = new FileOutputStream(lastName);
                doc.write(fos);
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (id == null || id <= 0) {
                projectAdmission.setCreateBy(createBy);
                //正常插入操作
                projectAdmissionService.save(projectAdmission);
                //1.分析明细
                if (analysisReports != null && analysisReports.size() > 0) {
                    for (AnalysisReport analysisReport : analysisReports) {
                        analysisReport.setProjectId(projectAdmission.getId());
                    }
                    for (AnalysisReport analysisReport : analysisReports) {
                        analysisReportService.save(analysisReport);
                    }
                }
            } else {
                //更新操作
                projectAdmission.setUpdateBy(createBy);
                projectAdmission.setId(id);
                projectAdmissionService.updateById(projectAdmission);
                //1.分析明细
                if (analysisReports != null && analysisReports.size() > 0) {
                    for (AnalysisReport analysisReport : analysisReports) {
                        analysisReport.setProjectId(projectAdmission.getId());
                    }
                    for (AnalysisReport analysisReport : analysisReports) {
                        analysisReportService.update(analysisReport, new QueryWrapper<AnalysisReport>().eq("project_id", projectAdmission.getId()));
                    }
                }
            }
        } catch (ProjectException e) {
            log.error("添加项目准入异常，异常信息：{}", e.getMessage());
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加项目准入失败");
        }

        Map<String, Object> result1 = new HashMap<>();
        result1.put("id", projectAdmission.getId());
        result1.put("fileUrl", projectAdmission.getFileUrl());
        return ResultVOUtil.success(result1);
    }

    /**
     * @return
     * @description: 导入excel并返回坐标数据
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/8 0008 10:05
     */
    @RequestMapping("/import")
    public ResultVO importExcel(HttpServletRequest req) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile file = multipartRequest.getFile("file");


        List<CoordinateVO> list = ExcelUtil.importExcel(file, 1, 1, CoordinateVO.class);
        System.out.println(list);
        List<Object> result = new ArrayList<>();
        if (list != null && list.size() > 0) {

            for (CoordinateVO coordinateVO : list) {
                Double[] coordinate = new Double[2];
                coordinate[0] = coordinateVO.getLongitude();
                coordinate[1] = coordinateVO.getLatitude();
                result.add(coordinate);

            }
        }
        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 坐标excel模板导出
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/8 0008 10:05
     */
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        List<CoordinateVO> list = new ArrayList<>();
        response.setHeader("Content-disposition", "attachment");
        response.setHeader("filename", "坐标模板.xls");
        response.setHeader("Content-Type", "application/msexcel");
        //导出操作
        ExcelUtil.exportExcel(list, "坐标", "坐标", CoordinateVO.class, "坐标模板.xls", response);
    }

//    @RequestMapping("/exportExcel")

 

}

