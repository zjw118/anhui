package com.gistone.controller;

import com.gistone.entity.St4ScsCt;
import com.gistone.pkname.PrimaryKey;
import com.gistone.pkname.Swagger;
import com.gistone.service.ISt4ScsCtService;
import com.gistone.util.RegUtil;
import com.gistone.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName SpaceDataController
 * @Description 空间数据管理
 * @Author xxh
 * @Date 2019/8/12 11:42
 * @Version 1.0
 **/
@RestController
@Api(value = "空间数据管理",tags = "空间数据管理",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/api/spaceData")
public class SpaceDataController {

    @Autowired
    private ISt4ScsCtService st4ScsCtService;



    @ApiOperation(value = "插入空间数据信息", notes = "此接口返回插入成功与否", response = Result.class)
    @PostMapping("/insertSpaceData")
    public Result insertSpaceData(HttpServletRequest request, @RequestBody @ApiParam(name = "空间数据信息", value = "json格式", required = true) Swagger<St4ScsCt> data) {
        St4ScsCt st4ScsCt = data.getData();
        if (st4ScsCt == null) {
            return Result.build(1001, "添加参数不能为空");
        }

        if (st4ScsCt.getCt007() == null || "".equals(st4ScsCt.getCt007())) {
            return Result.build(1001, "添加数据分类不能为空");
        }
        if (st4ScsCt.getCt003() == null || "".equals(st4ScsCt.getCt003())) {
            return Result.build(1001, "添加数据名称不能为空");
        }
        if(st4ScsCt.getCt007() == 1){
            if (st4ScsCt.getCt004() == null || "".equals(st4ScsCt.getCt004())) {
                return Result.build(1001, "添加数据URL不能为空");
            }
        }
        //当类型为数据时，必须有父级id
        if (st4ScsCt.getCt007() == 1) {
            if (st4ScsCt.getCt002() == null || "".equals(st4ScsCt.getCt002())) {
                return Result.build(1001, "添加数据类型不能为空");
            }
        }
        /*try {
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String UserId = JWT.decode(token).getAudience().get(0);
            st4ScsCt.setCt016(Integer.parseInt(UserId));//添加人id
        } catch (Exception e) {
            request.setAttribute("attrname","token无效，请重新登录");
            throw new RuntimeException("token无效，请重新登录");
        }*/

        return st4ScsCtService.insertSpaceData(st4ScsCt);
    }

    @ApiOperation(value = "修改空间数据信息", notes = "此接口返回修改成功与否", response = Result.class)
    @PostMapping("/updateSpaceData")
    public Result updateSpaceData(HttpServletRequest request,@RequestBody @ApiParam(name = "空间数据信息(修改ct001主键必须填写)", value = "json格式", required = true) Swagger<St4ScsCt> data) {
        St4ScsCt st4ScsCt = data.getData();
        if (st4ScsCt == null) {
            return Result.build(1001, "修改参数不能为空");
        }
        if (st4ScsCt.getCt001() == null || "".equals(st4ScsCt.getCt001())) {
            return Result.build(1001, "修改参数错误");
        }
        if (st4ScsCt.getCt003() == null || "".equals(st4ScsCt.getCt003())) {
            return Result.build(1001, "修改数据名称不能为空");
        }
        if (st4ScsCt.getCt004() == null || "".equals(st4ScsCt.getCt004())) {
            return Result.build(1001, "修改数据URL不能为空");
        }
        if (st4ScsCt.getCt007() == null || "".equals(st4ScsCt.getCt007())) {
            return Result.build(1001, "修改参数不能为空");
        }
        //当类型为数据时，必须有父级id
        if (st4ScsCt.getCt007() == 1) {
            if (st4ScsCt.getCt002() == null || "".equals(st4ScsCt.getCt002())) {
                return Result.build(1001, "修改数据类型不能为空");
            }
        }

        /*try {
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String UserId = JWT.decode(token).getAudience().get(0);
            st4ScsCt.setCt016(Integer.parseInt(UserId));//添加人id
        } catch (Exception e) {
            request.setAttribute("attrname","token无效，请重新登录");
            throw new RuntimeException("token无效，请重新登录");
        }*/
        return st4ScsCtService.updateSpaceData(st4ScsCt);
    }


    @ApiOperation(value = "删除空间数据信息", notes = "此接口返回删除成功与否", response = Result.class)
    @PostMapping("/deleteSpaceData")
    public Result deleteSpaceData(HttpServletRequest request,@RequestBody @ApiParam(name = "空间数据信息主键ct001", value = "json格式", required = true) Swagger<PrimaryKey> data) {
        PrimaryKey primaryKey = data.getData();
        if (primaryKey == null || "".equals(primaryKey)) {
            return Result.build(1001, "删除参数错误");
        }
        if (primaryKey.getPrimaryKey() == null || "".equals(primaryKey.getPrimaryKey())) {
            return Result.build(1001, "删除参数错误");
        }
        //先查询数据是否存在，存在后执行删除操作
        St4ScsCt st4ScsCt=st4ScsCtService.getById(primaryKey.getPrimaryKey());
        if(st4ScsCt==null){
            return  Result.build(1001, "删除参数错误");
        }

       /* try {
            String token = request.getHeader("token");// 从 http 请求头中取出 token
            String UserId = JWT.decode(token).getAudience().get(0);
            st4ScsCt.setCt016(Integer.parseInt(UserId));//添加人id
        } catch (Exception e) {
            request.setAttribute("attrname","token无效，请重新登录");
            throw new RuntimeException("token无效，请重新登录");
        }*/
        return st4ScsCtService.deleteSpaceData(st4ScsCt);
    }

    @ApiOperation(value = "根据pkid获取空间数据信息", notes = "此接口返回空间数据信息", response = Result.class)
    @PostMapping("/getSpaceDataInfo")
    public Result getSpaceDataInfo(HttpServletRequest request,@RequestBody @ApiParam(name = "空间数据信息主键ct001", value = "json格式", required = true) Swagger<PrimaryKey> data) {
        PrimaryKey primaryKey = data.getData();
        if (primaryKey == null || "".equals(primaryKey)) {
            return Result.build(1001, "参数错误");
        }
        if (primaryKey.getPrimaryKey() == null || "".equals(primaryKey.getPrimaryKey())) {
            return Result.build(1001, "参数错误");
        }
        //先查询数据是否存在，存在后执行删除操作
        St4ScsCt st4ScsCt = st4ScsCtService.getById(primaryKey.getPrimaryKey());
        if (st4ScsCt == null) {
            return Result.build(1001, "参数错误");
        }

        return Result.build(1000,"查询成功",st4ScsCt);

    }


        @ApiOperation(value = "根据分页信息查询空间数据信息数据列表", notes = "此接口返回空间数据分页列表", response = Result.class)
    @PostMapping("/listSpaceDataByPage")
    public Result listSpaceDataByPage(@RequestBody @ApiParam(name = "空间数据信息(必穿分页请求数据)", value = "json格式", required = true) Swagger<St4ScsCt> data) {
        St4ScsCt st4ScsCt = data.getData();
        if (st4ScsCt == null) {
            return Result.build(1001, "查询参数不能为空");
        }
        //每页条数
        if (!RegUtil.CheckParameter(st4ScsCt.getPageSize(), "Integer", null, false)) {
            return Result.build(1001, "查询pageSize不能为空");
        }
        //开始索引
        if (!RegUtil.CheckParameter(st4ScsCt.getPageNumber(), "Integer", null, false)) {
            return Result.build(1001, "查询pageNumber不能为空");
        }
        return st4ScsCtService.listSpaceDataByPage(st4ScsCt);
    }

    @ApiOperation(value = "查询空间数据信息数据树型列表", notes = "此接口返回空间数据树型列表", response = Result.class)
    @PostMapping("/listSpaceDataTree")
    public Result listSpaceDataTree(@RequestBody @ApiParam(name = "空间数据信息(可根据行政区划id做数据筛选)", value = "json格式", required = true) Swagger<St4ScsCt> data) {
        return st4ScsCtService.listSpaceDataTree((data.getData()==null)?(new St4ScsCt()):data.getData());
    }

    @ApiOperation(value = "查询空间数据分类数据", notes = "此接口返回空间数据分类", response = Result.class)
    @PostMapping("/listSpaceDataByType")
    public Result listSpaceDataByType(@RequestBody @ApiParam(name = "空间数据信息(可根据行政区划id做数据筛选)", value = "json格式", required = true) Swagger<St4ScsCt> data) {
        St4ScsCt st4ScsCt=(data.getData()==null)?(new St4ScsCt()):data.getData();
        return st4ScsCtService.listSpaceDataType(st4ScsCt);
    }

}
