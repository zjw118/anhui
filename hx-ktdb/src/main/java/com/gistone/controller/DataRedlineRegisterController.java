package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.DataRedlineRegister;
import com.gistone.service.IDataRedlineRegisterService;
import com.gistone.service.ILmPointService;
import com.gistone.util.ImportRedlineData;
import com.gistone.util.Result;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器  红线块
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/ktdb/dataRedlineRegister")
public class DataRedlineRegisterController {

    @Autowired
    private IDataRedlineRegisterService dataRedlineRegisterService;

    @Autowired
    private ILmPointService iLmPointService;


    /**
     * 查询红线块
     *
     * @param requestData
     * @return
     */
    @RequestMapping(value = "listAll", method = RequestMethod.POST)
    public Object listAll(@RequestBody Map<String, Object> requestData) {

        DataRedlineRegister date = new DataRedlineRegister();

        List<Map<String, Object>> list = dataRedlineRegisterService.listAll(date);

        return Result.ok(list);
    }

    @PostMapping("/allList")
    public ResultVO allList(){
        List<DataRedlineRegister> list = dataRedlineRegisterService.list(null);
        return ResultVOUtil.success(list);
    }



    /**
     * @param paramsMap
     * @return ResultVO
     * @description:红线列表
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/12 0012 13:46
     */
    @PostMapping("/list")
    public ResultVO getRedLineList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String boardNum = (String) dataParam.get("redLineNum");

        String code = (String) dataParam.get("code");

        //截取code做模糊查询
        String codes = null;
        if (StringUtils.isNotBlank(code)) {
            Integer level = iLmPointService.getLevelByCode(code);
            if (level != null && level > 0) {
                if (level == 1) {
                    codes = code.substring(0, 2);
                } else if (level == 2) {
                    codes = code.substring(0, 4);
                } else {
                    codes = code;
                }
            } else {
                codes = code;
            }
        }

        Integer pageNum = (Integer) dataParam.get("pageNum");
        Integer pageSize = (Integer) dataParam.get("pageSize");


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = dataRedlineRegisterService.getRedLineList(boardNum, codes, pageNum, pageSize);


        return ResultVOUtil.success(result);
    }

    /**
     * @return
     * @description: 导入红线数据
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/15 0015 16:24
     */
    @RequestMapping("/import")
    public void importRedline() {
        String path = "D:/epr/shp/2019-10-29/10/32c1108555154de1a52e226b7858dbd1.shp";
        ImportRedlineData importRedlineData = new ImportRedlineData();
        ArrayList<DataRedlineRegister> lmMarkerMobiles = importRedlineData.readShapeFile(path);

        List<DataRedlineRegister> list = dataRedlineRegisterService.list(null);
        Iterator<DataRedlineRegister> iterator = null;
        if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
            iterator = lmMarkerMobiles.iterator();
        }


        while (iterator.hasNext()) {
            DataRedlineRegister dataRedlineRegister = iterator.next();
            //过滤掉集合中已经有的数据
            for (DataRedlineRegister redlineRegister : list) {
                if (dataRedlineRegister.getSrldId() == redlineRegister.getSrldId()) {
                    //如果有重复的id则做更新
                    dataRedlineRegisterService.updateBy(dataRedlineRegister);
                    iterator.remove();
                }
            }
        }
        boolean b = dataRedlineRegisterService.saveBatch(lmMarkerMobiles);

    }

    /**
     * @return
     * @description: 更新表中的某字段
     * @params
     * @author zf1017@foxmail.com
     * @date 2019/6/10 0010 10:16
     */
    @RequestMapping("/exchange")
    public void exchange() {
        List<DataRedlineRegister> dataRedlineRegisters = dataRedlineRegisterService.list(null);
        for (DataRedlineRegister dataRedlineRegister : dataRedlineRegisters) {
            if (dataRedlineRegister.getSrldTarget() == null) {
                if ("01".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("水源涵养");
                }
                if ("02".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("生物多样性维护");
                }
                if ("03".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("水土保持");
                }
                if ("04".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("防风固沙");
                }
                if ("05".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("其他生态功能");
                }

                dataRedlineRegisterService.updateTarget(dataRedlineRegister.getSrldId(), dataRedlineRegister.getSrldTarget());

            }


        }

    }

    /**
     * @param paramsMap
     * @return com.gistone.VO.ResultVO
     * @description:编辑预设拐点数据
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/11/14 0014 19:33
     */
    @PostMapping("/updateRedline")
    public ResultVO updateRedline(@RequestBody Map<String, Object> paramsMap) {

        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) dataParam.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        DataRedlineRegister dataRedlineRegister = new DataRedlineRegister();
        dataRedlineRegister.setSrldId(id);
        String srldNum = (String) dataParam.get("srldNum");
        if (StringUtils.isNotBlank(srldNum)) {
            dataRedlineRegister.setSrldNumber(srldNum);
        }

        String type = (String) dataParam.get("srldType");
        if (StringUtils.isNotBlank(type)) {
            dataRedlineRegister.setSrldType(type);
        }

        String target = (String) dataParam.get("target");
        if (StringUtils.isNotBlank(target)) {
            dataRedlineRegister.setSrldTarget(target);
        }

        String area = (String) dataParam.get("area");

        if (StringUtils.isNotBlank(area)) {
            dataRedlineRegister.setSrldArea(area);
        }
        dataRedlineRegisterService.updateById(dataRedlineRegister);

        return ResultVOUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }


        Integer id = (Integer) params.get("id");
        if(id==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"红线id不能为空");
        }
        DataRedlineRegister dataRedline = dataRedlineRegisterService.getOne(new QueryWrapper<DataRedlineRegister>().eq("srld_id",id));

        String srldNumber = (String) params.get("srldNumber");
        if(StringUtils.isNotBlank(srldNumber)){
            dataRedline.setSrldNumber(srldNumber);
        }
        String type = (String) params.get("type");
        if(StringUtils.isNotBlank(type)){
            dataRedline.setSrldType(type);
        }
        String target = (String) params.get("target");
        if(StringUtils.isNotBlank(target)){
            dataRedline.setTarget(target);
        }
        String area = (String) params.get("area");
        if(StringUtils.isNotBlank(area)){
            dataRedline.setSrldArea(area);
        }
        String  name = (String) params.get("name");
        if(StringUtils.isNotBlank(name)){
            dataRedline.setSrldName(name);
        }
        String plant = (String) params.get("plant");
        if(StringUtils.isNotBlank(plant)){
            dataRedline.setSrldPlantType(plant);
        }
        String active = (String) params.get("active");
        if(StringUtils.isNotBlank(active)){
            dataRedline.setSrldActive(active);
        }
        String problem = (String) params.get("problem");
        if(StringUtils.isNotBlank(problem)){
            dataRedline.setSrldProblem(problem);
        }
        String control = (String) params.get("control");
        if(StringUtils.isNotBlank(control)){
            dataRedline.setSrldControl(control);
        }

//判断更新人加人是否为空
        dataRedlineRegisterService.updateBy(dataRedline);
        return ResultVOUtil.success();
    }


}

