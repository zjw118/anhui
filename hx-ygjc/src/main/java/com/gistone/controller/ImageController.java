package com.gistone.controller;

import com.alibaba.fastjson.JSON;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageConfig;
import com.gistone.entity.ImageNumber;
import com.gistone.entity.ImageTemp;
import com.gistone.mapper.ImageConfigMapper;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.ImageNumberMapper;
import com.gistone.pkname.Swagger;
import com.gistone.service.IImageTempService;
import com.gistone.service.ILmPointService;
import com.gistone.service.ImageConfigService;
import com.gistone.service.ImageService;
import com.gistone.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private IImageTempService iImageTempService;


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
    @ApiOperation(value = "image识别添加接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/insertImagerTemp")
    public ResultVO insertImagerTemp(@RequestBody @ApiParam(name = "任务批次添加接口", value = "json格式", required = true) Swagger<ImageTemp> data,
                               HttpServletRequest request) {
        ImageTemp it = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(it.getName())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "名称不能为空！");
        }
        if(!ObjectUtils.isNotNullAndEmpty(it.getZipUrl())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "识别的url不能为空！");
        }
        it.setUpdateDate(LocalDateTime.now());
        return ResultVOUtil.success(iImageTempService.save(it));

    }
    @ApiOperation(value = "image识别列表接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listImagerTemp")
    public ResultVO listImagerTemp(@RequestBody @ApiParam(name = "任务批次添加接口", value = "json格式", required = true) Swagger<ImageTemp> data,
                               HttpServletRequest request) {
        ImageTemp param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())||!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageSize和pageNumber不能为空！");
        }
        return  iImageTempService.listImageTemp(param);
    }

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
        String oriDir="";
        String finalDir="";
        String ftpurl="";
        try {
            oriDir = "D:\\epr\\attached\\shp";
            finalDir = "D:\\FTP\\epr\\image\\shptemp";
            ftpurl = ExcelUtils.copyDirectiory(oriDir, finalDir);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**这里是因为11.21演示所以在服务器的上放置了演示用的shp相关文件这里的地址是移动端加载的这里去掉了url必填项的检验
         *这里传递进去的是shape文件拷贝后存放的ftp地址
         *
         */

        String url =  params.get("url")==null?"": params.get("url").toString();

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
        service.insert(name,url,ftpurl, createBy, remark,createDate);
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
    public ResultVO getCountGroupByType(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer  imageId = (Integer) params.get("imageId");
        List<Map<String, Object>> result = service.getCountGroupByType(imageId);
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
    public ResultVO getAreaGroupByType(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }


        Integer  imageId = (Integer) params.get("imageId");
        List<Map<String, Object>> result = service.getAreaGroupByType(imageId);
        return ResultVOUtil.success(result);
    }

    @PostMapping("/getCountChange")
    public ResultVO getCountChange(){
        List<Map<String, Object>> result = service.getCountChange();
        return ResultVOUtil.success(result);
    }

    @PostMapping("/getAreaChange")
    public ResultVO getAreaChange(@RequestBody Map<String, Object> paramsMap){
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
//    @RequestMapping(value = "/audit", method = RequestMethod.POST)
//    public ResultVO audit(@RequestBody Map<String, Object> paramsMap) {
//        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
//        if (params == null) {
//            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
//        }
//        Image image = new Image();
//        String id = (String) params.get("id");
//        if (StringUtils.isBlank(id)) {
//            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "影像主键id不能为空");
//        }
//        if (null != params.get("evaluation")) {
//            image.setEvaluation(params.get("evaluation") + "");
//        }
//        if (null != params.get("sign")) {
//            image.setSign(Integer.valueOf(params.get("sign") + ""));
//        }
//        image.setId(Integer.valueOf(id));
//        image.setAuditDate(new Date());
//        return service.audit(image);
//    }



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
            return ResultVOUtil.success(imageNumberMapper.selectName2());
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
            Object name = params.get("name");
            if (null==name) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "name不能为空");
            }
            ImageNumber imageNumber = new ImageNumber();
            imageNumber.setName(name.toString());
            List<ImageNumber> imageNumbers = imageNumberMapper.selectImageNumber(imageNumber);
            //判断是否需要新增
            for (ImageNumber number : imageNumbers) {
                if(null==number.getId()){
                    ImageNumber add = new ImageNumber();
                    add.setImage_config_id(number.getImageConfigId());
                    add.setName(name.toString());
                    add.setNumber(0.0);
                    imageNumberMapper.insertImageNumber(add);
                }
            }
            return ResultVOUtil.success(imageNumberMapper.selectImageNumber(imageNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }


    /**
     * 修改
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/updateNumber", method = RequestMethod.POST)
    public ResultVO updateNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object json = params.get("json");
            if(null==json)
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "json不能为空！");
            JSONObject jsonObject = JSONObject.fromObject(json);

            for (Object o : jsonObject.keySet()){
                ImageNumber imageNumber = new ImageNumber();
                imageNumber.setId(Integer.valueOf(o.toString()));
                imageNumber.setNumber(Double.valueOf(jsonObject.get(o)+""));
                imageNumberMapper.updateImageNumber(imageNumber);
                imageNumber = null;
            }
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
        }
    }


    /**
     * 修改批次名
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/updateNume", method = RequestMethod.POST)
    public ResultVO updateNume(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object name = params.get("name");
            if(null==name)
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "原名称name不能为空！");
            Object data = params.get("data");
            if(null==data)
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "新名称data不能为空！");


            //判断名称是否重复
            List list = imageNumberMapper.selectImageNumber2(data.toString());
            if(0<list.size()){
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "名称已存在！");
            }

            ImageNumber imageNumber = new ImageNumber();
            imageNumber.setName(name.toString());
            imageNumber.setData(data.toString());
            int res = imageNumberMapper.updateNum(imageNumber);
            if(0>res){
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "修改失败");
        }
    }


    /**
     * 创建系数批次
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/addNumber", method = RequestMethod.POST)
    public ResultVO addNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            if(null==params.get("name")){
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "name不能为空！");
            }
            //判断名称是否重复
            List list = imageNumberMapper.selectImageNumber2(params.get("name").toString());
            if(0<list.size()){
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "批次名已存在！");
            }

            ImageNumber imageNumber = new ImageNumber();
            imageNumber.setName(params.get("name").toString());
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
     * 选择默认系数
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "/defaultNumber", method = RequestMethod.POST)
    public ResultVO defaultNumber(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object name = params.get("name");
            if (null==name) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "name不能为空");
            }
            return imageService.defaultNumber(name.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }




    //拐点-生成最新拐点SHP
    @RequestMapping(value = "/gdShp", method = RequestMethod.POST)
    public ResultVO gdShp(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object rc = params.get("rc");  //容差
            if (null==rc) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "容差rc不能为空");
            }
            return service.gdShp(Double.valueOf(rc.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }

    //拐点-保存拐点数据
    @RequestMapping(value = "/addGdShp", method = RequestMethod.POST)
    public ResultVO addGdShp(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params == null) {
                return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
            }
            Object data = params.get("data");
            if (null==data) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "拐点数据不能为空");
            }
            return service.gdShp2(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }


    //拐点-下载
    @RequestMapping(value = "/gdFile")
    public ResultVO gdFile(HttpServletResponse response) {
        try {
            return service.gdFile(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "下载失败");
        }
    }


    //拐点- 是否有最新拐点
    @RequestMapping(value = "/getGdFile")
    public ResultVO getGdFile(HttpServletResponse response) {
        try {
            return service.getGdFile();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "获取失败");
        }
    }






}
