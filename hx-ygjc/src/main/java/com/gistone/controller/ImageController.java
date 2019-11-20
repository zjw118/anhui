package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.ImageNumber;
import com.gistone.mapper.ImageConfigMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.ImageNumberMapper;
import com.gistone.service.ILmPointService;
import com.gistone.service.ImageConfigService;
import com.gistone.service.ImageService;
import com.gistone.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 影像数据表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-10-18
 */


@RestController
@RequestMapping("/api/ygjc/image")
public class ImageController {
    @Autowired
    private ImageService service;
    @Autowired
    private ImageMapper mapper;
    @Autowired
    private ILmPointService iLmPointService;
    @Autowired
    private ImageConfigMapper imageConfigMapper;
    @Autowired
    private ImageConfigService imageConfigService;
    @Autowired
    private ImageNumberMapper imageNumberMapper;
    @Autowired
    private ImageService imageService;


    @Value("${ftp_host}")
    private String ftpHost;
    @Value("${ftp_port}")
    private Integer ftpPort;
    @Value("${ftp_username}")
    private String ftpUserName;
    @Value("${ftp_password}")
    private String ftpPassword;
    @Value("${ftp_pt}")
    private String ftpPt;
    @Value("${ftp_url}")
    private String ftpUrl;


    /**
     * @param paramsMap
     * @return
     */
    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("name");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> result = service.list(pageNum, pageSize, name);
        return ResultVOUtil.success(result);
    }

    /**
     * @param params
     * @return
     */
    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        Image entity = service.getById(id);
        entity.setList(mapper.selectISt4ScsCd(id));
        String shpStr = ShpUtil.readShapeFileToStr(entity.getShp(), 1) + "";
        entity.setShp(shpStr);
        return ResultVOUtil.success(entity);
    }


    /**
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String name = (String) params.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "名称不能为空");
        }
        String url = (String) params.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "地址不能为空");
        }
        String createDate = (String) params.get("createDate");
        if (StringUtils.isBlank(createDate)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "createDate不能为空");
        }
        String remark = (String) params.get("remark");

        //判断添加人是否为空
        Integer createBy = (Integer) params.get("createBy");
        if (createBy == null || createBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "创建人不能为空");
        }
        service.insert(name, url, createBy, remark,createDate);
        return ResultVOUtil.success();
    }


    /**
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        List<Integer> id = (List<Integer>) params.get("id");
        if (id == null || id.size() < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }

    /**
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) params.get("id");
        if (id == null || id <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "记录id不能为空");
        }

        String name = (String) params.get("name");
        if (StringUtils.isBlank(name)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "名称不能为空");
        }

        String url = (String) params.get("url");
        if (StringUtils.isBlank(url)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "地址不能为空");
        }

        Integer updateBy = (Integer) params.get("updateBy");
        if (updateBy == null || updateBy <= 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "更新人不能为空");
        }
        String remark = (String) params.get("remark");

        //判断更新人加人是否为空
        service.edit(id, name, url, updateBy, remark);
        return ResultVOUtil.success();
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:人类活动解译按类型统计面积
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/6 0006 10:23
     */
    @PostMapping("/getRlhdTotal")
    public ResultVO getRlhdTotal() {
        List<Map<String, Object>> result = service.getRlhdTotal();
        return ResultVOUtil.success(result);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:人类活动解译数据，按类型统计个数
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/20 0020 10:17
     */
    @PostMapping("/getCountGroupByType")
    public ResultVO getCountGroupByType() {
        List<Map<String, Object>> result = service.getCountGroupByType();
        return ResultVOUtil.success(result);
    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:人类活动解译数据，按类型统计面积
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/20 0020 10:18
     */
    @PostMapping("/getAreaGroupByType")
    public ResultVO getAreaGroupByType() {
        List<Map<String, Object>> result = service.getAreaGroupByType();
        return ResultVOUtil.success(result);
    }

    @PostMapping("/getCountChange")
    public ResultVO getCountChange(){
        List<Map<String, Object>> result = service.getCountChange();
        return ResultVOUtil.success(result);
    }

    @PostMapping("/getAreaChange")
    public ResultVO getAreaChange(){
        List<Map<String, Object>> result = service.getAreaChange();
        return ResultVOUtil.success(result);
    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:核查点数量统计
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/6 0006 9:54
     */
    @PostMapping("/getPointTotal")
    public ResultVO getPointTotal(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        //获取当前日期和前十五天日期
        LocalDate currentTime = LocalDate.now();
        LocalDate beforeTime = currentTime.minusDays(14);


        String code = (String) dataParam.get("code");
        /*if (StringUtils.isBlank(code)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "行政区划号不能为空");
        }*/

        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1 && code.length() > 3) {
                    codes = code.substring(0, 2);
                } else if (level == 2 && code.length() > 5) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }
        Map<String, Object> result = new HashMap<>();
        //获取
        //获取界桩统计
        List<Map<String, Object>> markerList = service.getCount(codes, currentTime, beforeTime);
        int count = service.getBeforeCount(codes, beforeTime);

        //获取调查表统计
//        List<Map<String, Object>> surveyList = totalService.getSurveyCount(codes, currentTime, beforeTime);
        result.put("markerCount", markerList);
        result.put("beforeMarkerCount", count);

        return ResultVOUtil.success(result);
    }


    /**
     * 添加配置
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/addConfig", method = RequestMethod.POST)
    public ResultVO addConfig(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");

        Object name = params.get("name");
        Object parentid = params.get("parentid");
        Object type = params.get("type");
        Object orders = params.get("orders");
        if (null == name)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "name不能为空！");
        if (null == parentid)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "parentid不能为空！");
        if (null == type)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "type不能为空！");

        ImageConfig imageConfig = new ImageConfig();

        if (null != name)
            imageConfig.setName(name + "");
        if (parentid != null)
            imageConfig.setParentid(Integer.valueOf(parentid + ""));
        if (null != type)
            imageConfig.setType(Integer.valueOf(type + ""));
        if (null != orders)
            imageConfig.setOrders(Integer.valueOf(orders + ""));
        if (0 < imageConfigMapper.insertImageConfig(imageConfig))
            return ResultVOUtil.success();
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");

    }


    /**
     * 配置删除
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/deleteConfig", method = RequestMethod.POST)
    public ResultVO deleteConfig(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        Object id = params.get("id");
        if (null == id)
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        //删除配置表
        ImageConfig imageConfig = new ImageConfig();
        imageConfig.setId(Integer.valueOf(id + ""));
        int i = imageConfigMapper.deleteImageConfig(imageConfig);

        //删除系数表-无需
        if (0 < i)
            return ResultVOUtil.success();
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除失败");
    }

    /**
     * 配置树形结构
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResultVO config(@RequestBody Map<String, Object> paramsMap) {
        try {
            return imageConfigService.selectImageConfig();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }

    /**
     * 配置修改
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/updateConfig", method = RequestMethod.POST)
    public ResultVO updateConfig(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null)
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        Object id = params.get("id");
        if (null == id)
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        Object name = params.get("name");
        Object parentid = params.get("parentid");
        Object type = params.get("type");
        Object orders = params.get("orders");

        ImageConfig imageConfig = new ImageConfig();
        imageConfig.setId(Integer.valueOf(id + ""));
        if (null != name)
            imageConfig.setName(name + "");
        if (null != parentid)
            imageConfig.setParentid(Integer.valueOf(parentid + ""));
        if (null != type)
            imageConfig.setType(Integer.valueOf(type + ""));
        if (null != orders)
            imageConfig.setOrders(Integer.valueOf(orders + ""));

        if (0 < imageConfigMapper.updateImageConfig(imageConfig))
            return ResultVOUtil.success();
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
    }


    /**
     * 审核详情
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getAudit", method = RequestMethod.POST)
    public ResultVO getAudit(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Object id =  params.get("id");
        if (null==id) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像主键id不能为空");
        }
        return service.getAudit(Integer.valueOf(id+""));
    }


    /**
     * 审核计算
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/addAudit", method = RequestMethod.POST)
    public ResultVO addAudit(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String id = (String) params.get("id");
        if (StringUtils.isBlank(id)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "主键id不能为空");
        }

        Object json = params.get("json");
        if (null==json) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "系数json不能为空");
        }

        JSONObject job = JSONObject.fromObject(json);
        return service.addAudit(Integer.valueOf(id),job);

    }

    /**
     * 开始审核
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResultVO audit(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        Image image = new Image();
        String id = (String) params.get("id");
        if (StringUtils.isBlank(id)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像主键id不能为空");
        }
        if (null != params.get("evaluation")) {
            image.setEvaluation(params.get("evaluation") + "");
        }
        if (null != params.get("sign")) {
            image.setSign(Integer.valueOf(params.get("sign") + ""));
        }
        image.setId(Integer.valueOf(id));
        image.setAuditDate(new Date());
        return service.audit(image);
    }


    /**
     * 人类活动类型列表
     *
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/getConfig", method = RequestMethod.POST)
    public ResultVO getConfig(@RequestBody Map<String, Object> paramsMap) {
        try {
            return ResultVOUtil.success(imageConfigMapper.getImageConfigAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }


    //系数批次名列表
    @RequestMapping(value = "/getNumberNames", method = RequestMethod.POST)
    public ResultVO getNumberNames(@RequestBody Map<String, Object> paramsMap) {
        try {
            return ResultVOUtil.success(imageNumberMapper.selectName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }

    //系数批次名查询列表
    @RequestMapping(value = "/getNumberByName", method = RequestMethod.POST)
    public ResultVO getNumberByName(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            String name = (String) params.get("name");
            if (StringUtils.isBlank(name)) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "name不能为空");
            }

            ImageNumber imageNumber = new ImageNumber();
            imageNumber.setName(name);
            return ResultVOUtil.success(imageNumberMapper.selectImageNumber(imageNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }


    //系数修改
    @RequestMapping(value = "/updateNumber", method = RequestMethod.POST)
    public ResultVO updateNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            String id = (String) params.get("id");
            if (StringUtils.isBlank(id)) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
            }

            ImageNumber imageNumber = new ImageNumber();
            imageNumber.setId(Integer.valueOf(id));

            if(null!=params.get("imageConfigId"))
            imageNumber.setImage_config_id(Integer.valueOf(params.get("imageConfigId")+""));
            if(null!=params.get("number"))
            imageNumber.setNumber(Double.valueOf(params.get("number")+""));
            if(null!=params.get("name"))
            imageNumber.setName(params.get("name")+"");
            return ResultVOUtil.success(imageNumberMapper.updateImageNumber(imageNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
        }
    }



    //添加系数
    @RequestMapping(value = "/addNumber", method = RequestMethod.POST)
    public ResultVO addNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            ImageNumber imageNumber = new ImageNumber();
            if(null!=params.get("imageConfigId"))
            imageNumber.setImage_config_id(Integer.valueOf(params.get("imageConfigId")+""));
            if(null!=params.get("number"))
            imageNumber.setNumber(Double.valueOf(params.get("number")+""));
            if(null!=params.get("name"))
            imageNumber.setName(params.get("name")+"");
            return ResultVOUtil.success(imageNumberMapper.insertImageNumber(imageNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "添加失败");
        }
    }


    //删除系数
    @RequestMapping(value = "/deleteNumber", method = RequestMethod.POST)
    public ResultVO deleteNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            String name = (String) params.get("name");
            if (StringUtils.isBlank(name)) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "name不能为空");
            }
            return ResultVOUtil.success(imageNumberMapper.deleteImageName(name));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除失败");
        }
    }


    /**
     * 获取默认系数
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/oldNumber", method = RequestMethod.POST)
    public ResultVO oldNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object id = params.get("id");
            if (null==id) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
            }
            return imageService.oldNumber(Integer.valueOf(id.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }




}
