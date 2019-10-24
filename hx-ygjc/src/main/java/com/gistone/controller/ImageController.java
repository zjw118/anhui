package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.service.ImageService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import com.gistone.util.ShpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
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




    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        Image entity = service.getById(id);
        entity.getUrl();
        ShpUtil.readShapeFileToStr("D:"+entity.getUrl(),1);
        entity.setShp(ShpUtil.readShapeFileToStr("D:"+entity.getUrl(),1)+"");

        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String name = (String) params.get("name");
        if(StringUtils.isBlank(name)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"名称不能为空");
        }

        String url = (String) params.get("url");
        if(StringUtils.isBlank(url)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"地址不能为空");
        }

        String remark = (String) params.get("remark");

       /* if(StringUtils.isBlank(remark)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"");
        }*/
        //判断添加人是否为空
        Integer createBy = (Integer) params.get("createBy");
        if(createBy==null||createBy<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"创建人不能为空");
        }

        service.insert(name,url,createBy,remark);
        return ResultVOUtil.success();
    }


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


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) params.get("id");
        if(id==null||id<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"记录id不能为空");
        }

        String name = (String) params.get("name");
        if(StringUtils.isBlank(name)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"名称不能为空");
        }

        String url = (String) params.get("url");
        if(StringUtils.isBlank(url)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"地址不能为空");
        }

        Integer updateBy = (Integer) params.get("updateBy");
        if(updateBy==null||updateBy<=0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"更新人不能为空");
        }
        String remark = (String) params.get("remark");

        //判断更新人加人是否为空
        service.edit(id,name,url,updateBy,remark);
        return ResultVOUtil.success();
    }



}
