package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCbd;
import com.gistone.service.St4ScsCbdService;
import com.gistone.util.DateUtils;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 移动端提交检测表 前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @version v1.0
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/api/st4ScsCbd")
public class St4ScsCbdController {
    @Autowired
    private St4ScsCbdService service;

    @PostMapping("/list")
    public ResultVO getList(@RequestBody Map<String, Object> paramsMap ) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        String name = (String) params.get("userName");
        String time = (String) params.get("time");
        String title = (String) params.get("title");
        String content = (String) params.get("content");

        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        Map<String, Object> result = service.list(pageNum, pageSize, name,time,title,content);
        return ResultVOUtil.success(result);
    }


    @PostMapping("/detail")
    public ResultVO getById(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        St4ScsCbd entity = service.getById(id);
        return ResultVOUtil.success(entity);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO add(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        St4ScsCbd entity = new St4ScsCbd();
        String oprateUser = (String) params.get("user");
        if(StringUtils.isBlank(oprateUser)){
           return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"操作人不能为空");
        }

        String time = (String) params.get("time");
        if(StringUtils.isNotBlank(time)){
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

            Date date2 = null;
            try {
                date2 = format2.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date = DateUtils.addDateDays(date2, 1);
            entity.setCbd003(date);
        }else {
            entity.setCbd003(new Date());
        }




        entity.setCbd002(oprateUser);
        String midContent = (String) params.get("midContent");
        if(StringUtils.isBlank(midContent)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"内容不能为空");
        }
        entity.setCbd005(midContent);
        String afterContent = (String) params.get("afterContent");
        if(StringUtils.isBlank(afterContent)){
          return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"内容不能为空");
        }

        String users = (String) params.get("users");
        if(StringUtils.isBlank(users)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"发布对象不能为空");
        }
        entity.setUser(users);
        entity.setCbd006(afterContent);

        Integer verify = (Integer) params.get("verify");
        if(verify==null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"审核状态不能为空");
        }
        entity.setVerify(verify);
        entity.setCreateDate(new Date());

        //判断添加人是否为空
       service.insert(entity);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/delete")
    public ResultVO delete(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

       Integer id = (Integer)params.get("id");
        if (id == null || id < 0) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "id不能为空");
        }
        service.delete(id);
        return ResultVOUtil.success();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO update(@RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }

        Integer id = (Integer) params.get("id");
        if(id==null||id<0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        St4ScsCbd entity = service.getById(id);


        String oprateUser = (String) params.get("user");
        if(StringUtils.isNotBlank(oprateUser)){
            entity.setCbd002(oprateUser);
        }

        String time = (String) params.get("time");
        if(StringUtils.isNotBlank(time)){


            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = null;

            try {
                date2 = format2.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date = DateUtils.addDateDays(date2, 1);
            entity.setCbd003(date);
        }else{
            entity.setCbd003(new Date());
        }

        String midContent = (String) params.get("midContent");
        if(StringUtils.isNotBlank(midContent)){
            entity.setCbd005(midContent);
        }

        String afterContent = (String) params.get("afterContent");
        if(StringUtils.isNotBlank(afterContent)){
            entity.setCbd006(afterContent);
        }

        String user = (String) params.get("users");
        if(StringUtils.isNotBlank(user)){
            entity.setUser(user);
        }
        Integer verify = (Integer) params.get("verify");
        if(verify!=null){
            entity.setVerify(verify);
        }
//判断更新人加人是否为空
        service.edit(entity);
        return ResultVOUtil.success();
    }

    @PostMapping("verify")
    public ResultVO verify(@RequestBody Map<String, Object> paramsMap){
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if(id==null||id<0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        St4ScsCbd st4ScsCbd = new St4ScsCbd();
        st4ScsCbd.setVerify(1);
        service.update(st4ScsCbd,new QueryWrapper<St4ScsCbd>().eq("CBD001",id));


        return ResultVOUtil.success();
    }

    @PostMapping("/refuse")
    public ResultVO refuse(@RequestBody Map<String, Object> paramsMap){
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "请求数据data不能为空！");
        }
        Integer id = (Integer) params.get("id");
        if(id==null||id<0){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        }
        St4ScsCbd st4ScsCbd = new St4ScsCbd();
        st4ScsCbd.setVerify(2);
        service.update(st4ScsCbd,new QueryWrapper<St4ScsCbd>().eq("CBD001",id));


        return ResultVOUtil.success();
    }

}
