package com.gistone.controller;
import com.gistone.service.FileUpAndDownService;
import com.gistone.util.ObjectUtils;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Api(value="(安徽移动端用)上传接口 文件的参数名是'file','dirId'参数是上传后后台存的文件夹的标识存在以下值分别对应不同模块：" +
        "nr_system(系统) nr_ledger(核查) nr_object(航点)  nr_line(航迹) nr_reserve(保护地边界) nr_point(物种) decode(解译)",tags = "上传接口")
@RestController
@RequestMapping("/system/upload")
public class FileUploadController {
    @Autowired
    private FileUpAndDownService fileUpAndDownService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
    @ApiOperation(value="上传通用接口 文件的参数名是'file','dirId'参数是上传后后台存的文件夹的标识" +
            "存在以下值分别对应不同模块：nr_system(系统) nr_ledger(核查) nr_object(航点) nr_line(航迹) " +
            "nr_reserve(保护地边界) nr_temp(image临时用)\",tags = \"上传接口",notes = "上传通用接口")
    @RequestMapping(value = "/fileUpload" ,method = RequestMethod.POST)
    @ResponseBody
    public Result FileUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("dirId") String dirId) {

        Result result = new Result();
        try {
            if(!ObjectUtils.isNotNullAndEmpty(dirId)){
                return Result.build(1001,"类型"+ ResultMsg.MSG_1001);
            }
            Map<String, Object> resultMap = upload(files,dirId);
            if (ResultMsg.MSG_1000.equals(resultMap.get("result"))) {
                result.setStatus(1000);
                result.setMsg("上传"+ResultMsg.MSG_1000);
                result.setData(resultMap.get("data"));
                return result;
            }
            result.setData(resultMap);
            result.setStatus(1000);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件上传异常", e.getMessage());
            Result.build(1006,ResultMsg.MSG_1006);
        }
        return result;
    }

    private Map<String, Object> upload(MultipartFile[] files,String phoType)  {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if (files!=null&&files.length>0) {
                Map<String, Object> picMap = fileUpAndDownService.uploadPicture(files,phoType);
                if (ResultMsg.MSG_1000.equals(picMap.get("result"))) {
                    return picMap;
                } else {
                    returnMap.put("result", ResultMsg.MSG_1006);
                    returnMap.put("msg", picMap.get("result"));
                }
            } else {
                LOGGER.info(">>>>>>上传图片为空文件");
                returnMap.put("result", ResultMsg.MSG_1006);
                returnMap.put("msg", "上传的图片为空文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
