package com.gistone.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.annotation.PassToken;
import com.gistone.entity.*;
import com.gistone.mapper.St4ScsCkMapper;
import com.gistone.pkname.Swagger;
import com.gistone.service.*;
import com.gistone.swagger.SharePoint;
import com.gistone.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 任务批次表 前端控制器
 * </p>
 *
 * @author LiuXiong
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/api/task")
@Api(value = "API-UserManage", tags = "任务批次表接口", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @Autowired
    private ISt4SysSaService st4SysSaService;
    @Autowired
    private ISt4ScsClService iSt4ScsClService;

    @Autowired
    private ISt4PoSaCzService iSt4PoSaCzService;
    @Autowired
    private ISt4ScsCzService iSt4ScsCzService;
    @Autowired
    private RlhdGroupService rlhdGroupService;
    @Autowired
    private StatisService statisService;
    @Autowired
    private ISt4ScsCoService iSt4ScsCoService;
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;

    @ApiOperation(value = "导出审核统计word", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("export")
    public ResultVO examineExportWord(HttpServletRequest request, HttpServletResponse response){
        St4ScsCk ck = new St4ScsCk();
        ck.setGroupByName("sd001");
        List<Map> list = st4ScsCkMapper.selectSt4ScsCkMap(ck);
        //Map<String,St4ScsCk> map = list.stream().collect(Collectors.toMap(St4ScsCk::getKey,c -> c));
        Map mapp = new HashMap();
        List<St4ScsCk> listNum = st4ScsCkMapper.selectSt4ScsCk(ck);
        List<String> totalNum = listNum.stream().map(St4ScsCk::getTzNum).collect(Collectors.toList());
        Integer totalCount = 0;
        for (String t:totalNum) {
            totalCount+=Integer.valueOf(t);
        }
        mapp.put("${total}", totalCount);
        totalCount = 0;
        List<String> t1Num = listNum.stream().map(St4ScsCk::getEmaminedNum).collect(Collectors.toList());
        for (String t:t1Num) {
            totalCount+=Integer.valueOf(t);
        }
        mapp.put("${t1}", totalCount);
        totalCount = 0;
        List<String> t2Num = listNum.stream().map(St4ScsCk::getUnEmaminedNum).collect(Collectors.toList());
        for (String t:t2Num) {
            totalCount+=Integer.valueOf(t);
        }
        mapp.put("${t2}", totalCount);
        totalCount = 0;
        List<String> t3Num = listNum.stream().map(St4ScsCk::getBackNum).collect(Collectors.toList());
        for (String t:t3Num) {
            totalCount+=Integer.valueOf(t);
        }
        mapp.put("${t3}", totalCount);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String finalPath = configUtils.getExcel_PATH()+sdf.format(new Date())+"江苏省生态红线问题斑块审核报告.docx";
        WordUtil.getWord(mapp, list, finalPath, list.size());
        Map map = new HashMap();
        map.put("path", finalPath.substring(finalPath.indexOf(":")+1));
        return ResultVOUtil.success(map);

//        //这里是我说的一行代码
//        WordUtil.SimpleWordExport("D:\\1\\aa.docx","D:\\1\\","aaa.docx",params,request,response);
    }



    /*@ApiOperation(value = "问题点批次", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listCheckPointToView")
    public Result listCheckPointToView(@RequestBody @ApiParam(name = "问题点批次", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        return iSt4ScsClService.listForView(param);
    }*/

    /**
     * 任务批次添加接口
     * @param data
     * @return
     */
    @ApiOperation(value = "任务批次添加接口(参数传递的时候ledgerIdList是必传项格式是ledgerIdList:[1,2,3])", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/insertTask")
    public ResultVO insertTask(@RequestBody @ApiParam(name = "任务批次添加接口", value = "json格式", required = true) Swagger<St4ScsCl> data,
                               HttpServletRequest request) {
        St4ScsCl param = data.getData();
        LocalDateTime date = LocalDateTime.now();
        //安徽项目没有巡护所以去掉验证
        /*if(param.getCl004()==null){
            return  Result.build(1001,"任务类型cl002"+ResultMsg.MSG_1001);
        }*/
        SysUser seUser = new SysUser();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1";// JWT.decode(token).getAudience().get(0);
        seUser.setId(Integer.valueOf(userId));
        param.setCl013(seUser.getId());
        param.setCl014(date);
        param.setCl003("1");
        String taskName = param.getCl002();
        if(!ObjectUtils.isNotNullAndEmpty(taskName)){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "任务名称不能为空！");
        }
        QueryWrapper<St4ScsCl> clQueryWrapper = new QueryWrapper<>();
        clQueryWrapper.eq("CL002", taskName);
        clQueryWrapper.eq("CL012",1);

        St4ScsCl cl = iSt4ScsClService.getOne(clQueryWrapper);
        if(cl!=null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "任务名称重复！");
        }
        List<Integer> ledgerIdList = param.getLedgerIdList();
        if(ledgerIdList==null||ledgerIdList.size()<1){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "绑定台账不能为空！");
        }

        return iSt4ScsClService.insertTask(param,seUser);
    }
    /**
     * 台账下拉多选接口
     * @param spSwagger
     * @return
     */
    @ApiOperation(value="台账下拉多选接口",notes = "台账下拉多选接口",response = St4ScsCo.class)
    @RequestMapping(value="/ledgerSelect",method = RequestMethod.POST)
    public ResultVO ledgerSelect(@ApiParam(name="台账下拉多选接口", value="json格式", required=true)@RequestBody Swagger<SharePoint> spSwagger) {

        ResultCp resultCp = new ResultCp();
    QueryWrapper<RlhdGroup> rlhdGroupQueryWrapper = new QueryWrapper<>();
        rlhdGroupQueryWrapper.eq("del_flag",1);
    rlhdGroupQueryWrapper.orderByDesc("create_date");
        List<RlhdGroup> coList = rlhdGroupService.list(rlhdGroupQueryWrapper);

        resultCp.setData(coList);
        return ResultVOUtil.success(resultCp);

    }

    /**
     * 任务批次修改接口
     * @param data
     * @return
     */
    @ApiOperation(value = "任务批次修改接口", notes = "此接口返回问题点批次数据", response = St4ScsCl.class)
    @PostMapping("/updateTask")
    public ResultVO updateTask(@RequestBody @ApiParam(name = "任务批次修改接口", value = "json格式", required = true) Swagger<St4ScsCl> data
            , HttpServletRequest request) {
        St4ScsCl param = data.getData();
        param.setCl003("1");
        if(param.getCl001()==null){
           return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "批次任务ciId不能为空！");
        }
        List<Integer> ledgerIdList = param.getLedgerIdList();

        if(ledgerIdList==null||ledgerIdList.size()<1){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据台账不能为空！");
        }
        SysUser seUser = new SysUser();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1";
        return iSt4ScsClService.updateTask(param,seUser);

    }

    /**
     * 任务批次删除接口
     * @param data
     * @return
     */

    @ApiOperation(value = "任务批次删除接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/deleteTask")
    public ResultVO deleteTask(@RequestBody @ApiParam(name = "任务批次修改接口", value = "json格式", required = true) Swagger<St4ScsCl> data, HttpServletRequest request) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "批次任务ciId不能为空！");
        }
        param.setCl012(0);
        SysUser seUser = new SysUser();
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1";
        return iSt4ScsClService.deleteTask(param,seUser);
    }

    /**
     * 任务批次列表接口
     * @param data
     * @return
     */
    @ApiOperation(value = "任务批次列表接口(模糊查询字段cl002(任务名称) cl010(年份) )", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listTask")
    public ResultVO listTask(@RequestBody @ApiParam(name = "任务批次列表接口", value = "json格式", required = true)Swagger<St4ScsCl> data, HttpServletRequest request) {
        St4ScsCl param = data.getData();
        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())&&!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageSize和pageNumber不能为空！");
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId ="1" ;//JWT.decode(token).getAudience().get(0);
        SysUser serUser = new SysUser();
        serUser.setId(Integer.valueOf(userId));
        return  iSt4ScsClService.listTask(param,serUser);

    }
    /**
     * 任务批次下拉框列表接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "(安徽用)任务批次下拉框列表接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/listTaskSelect")
    public ResultVO listTaskSelect(@RequestBody @ApiParam(name = "任务批次下拉框列表接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("CL012",1);
        ResultCp res = new ResultCp();
        res.setData(iSt4ScsClService.list(wrapper));
        return  ResultVOUtil.success(res);
    }

    /**
     * 任务批次单个详情接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "任务批次单个详情接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/getTaskById")
    public ResultVO getTaskById(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "任务批次CL001不能为空！");
        }
       return  iSt4ScsClService.getTaskDetail(param);
    }
    /**
     * 任务斑块查询接口接口
     * @param data
     * @return
     */
    @PassToken
    @ApiOperation(value = "任务斑块查询接口接口", notes = "野外监测任务地图", response = Result.class)
    @PostMapping("/listCdByTask")
    public ResultVO listCdByTask(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
       return  iSt4ScsClService.listCdByTask(param);
    }
    /**
    * (安徽用)任务导入
     * @return
     */
    @PassToken
    @ApiOperation(value = "(安徽用)任务导入", notes = "(安徽用)任务导入", response = Result.class)
    @PostMapping("/importTask")
    public ResultVO importTask(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
            if(!file.isEmpty()) {
                // 在定义一个文件上传后的临时存储路径
                request.getServletContext().getRealPath("/");
                String temp_savepath = configUtils.getExcel_PATH()+"ledger/temp/importTask" ;
                File dirFile = new File(temp_savepath);
                if(!dirFile.exists()&&!dirFile.isDirectory()){
                    //检查临时目录是否存在，不存在则创建
                    dirFile.mkdirs();
                }
                String aaa =PictureUtilsCh.getDiyTempFilePath(temp_savepath, file);
                try{
                    return iSt4ScsClService.readExcel(aaa);
                }catch (Exception e){
                    e.printStackTrace();
                    return ResultVOUtil.error("1222","处理结果失败");
                }
            }
        return ResultVOUtil.error("1444","服务器未读取到数据，请确认所上传excel是否有信息");
    }
    /**
     * (安徽用)任务导出
     * @return
     */
    @PassToken
    @ApiOperation(value = "(安徽用)任务导出", notes = "(安徽用)任务导出", response = Result.class)
    @PostMapping("/exportTask")
    public ResultVO exportTask(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data,
                               HttpServletResponse response) {
            St4ScsCl cl = data.getData();

            return iSt4ScsClService.exportTask(cl);
    }
        /**
         * 新建巡护小组
         * @param data
         * @return
         *//*
    @PassToken
    @ApiOperation(value = "新建巡护小组*****", notes = "新建巡护小组", response = St4ScsCz.class)
    @PostMapping("/insertGroup")
    public Result insertGroup(@RequestBody @ApiParam(name = "新建巡护小组", value = "json格式", required = true)
                                          Swagger<St4ScsCzSwagger> data,HttpServletRequest request) {
        St4ScsCzSwagger param = data.getData();
        if(param.groupName==null){
            return  Result.build(1001,"巡护人员分组名称groupName"+ResultMsg.MSG_1001);
        }
        QueryWrapper<St4ScsCz> wrapper = new QueryWrapper<>();
        wrapper.eq("CZ002",param.groupName.trim());
        wrapper.eq("CZ003",1);
        if(iSt4ScsCzService.list(wrapper).size()>0){
            return  Result.build(1006,"巡护小组名称不能重复");
        }
        if(param.captainId==null){
            return  Result.build(1001,"巡护人员组长ID"+ResultMsg.MSG_1001);
        }
        if(param.uidList.size()<1){
            return  Result.build(1001,"巡护人员集合uidList"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.insertGroupData(param,request);
    }*/
    /*@PassToken
    @ApiOperation(value = "展示巡护小组应该显示的人(无需传参)", notes = "展示巡护小组应该显示的人", response = St4ScsCz.class)
    @PostMapping("/showUser")
    public Result showUserInCheck(@RequestBody @ApiParam(name = "展示巡护小组应该显示的人", value = "json格式", required = true) Swagger<T> data) {

        return  iSt4ScsCzService.showUserInCheck();
    }*/
   /* *//**
     * 修改巡护人员小组信息
     * @param data
     * @return
     *//*
    @PassToken
    @ApiOperation(value = "修改巡护小组信息*****", notes = "修改巡护人员小组信息", response = St4ScsCz.class)
    @PostMapping("/updateGroup")
    public Result updateGroup(@RequestBody @ApiParam(name = "修改巡护人员小组信息", value = "json格式", required = true) Swagger<St4ScsCzSwagger> data) {
        St4ScsCzSwagger param = data.getData();

        if(param.groupId==null){
            return  Result.build(1001,"巡护人员分组Id"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.updateGroupData(param);
    }*/

    /**
     * 删除巡护人员分组
     * @param data
     * @return
     */
   /* @PassToken
    @ApiOperation(value = "删除巡护人员分组******", notes = "删除巡护人员分组", response = St4ScsCz.class)
    @PostMapping("/deleteGroup")
    public Result deleteGroup(@RequestBody @ApiParam(name = "删除巡护人员分组", value = "json格式", required = true) Swagger<St4ScsCz> data) {
        St4ScsCz param = data.getData();

        if(iSt4ScsCzService.getById(param.getCz001())==null){
            return  Result.build(1001,"巡护人员分组cz001"+ResultMsg.MSG_1001);
        }
        param.setCz003(0);
        if(iSt4ScsCzService.updateById(param)){
            QueryWrapper<St4PoSaCz> wrapper = new QueryWrapper<>();
            wrapper.eq("CZ001",param.getCz001());
            if(iSt4PoSaCzService.remove(wrapper)){
                return Result.build(1000,"删除"+ResultMsg.MSG_1000);
            }else{
                return Result.build(1003,"删除失败，"+ResultMsg.MSG_1003);
            }

        }
        return  Result.build(1005,ResultMsg.MSG_1005);
    }*/

    /**
     * 人员分组详情接口
     * @param data
     * @return
     */
    /*@PassToken
    @ApiOperation(value = "人员分组详情接口******", notes = "人员分组详情接口", response = St4ScsCz.class)
    @PostMapping("/getGroupUserDetail")
    public Result getGroupUserDetail(@RequestBody @ApiParam(name = "人员分组详情接口", value = "json格式", required = true) Swagger<St4ScsCz> data) {
        St4ScsCz param = data.getData();

        if(iSt4ScsCzService.getById(param.getCz001())==null){
            return  Result.build(1001,"巡护人员分组cz001"+ResultMsg.MSG_1001);
        }
        return  iSt4ScsCzService.getGroupUserDetail(param);
    }*/
    /**
     * 巡护人员分组列表
     * @param data
     * @return
     */
   /* @PassToken
    @ApiOperation(value = "巡护人员分组列表******", notes = "巡护人员分组列表", response = St4ScsCz.class)
    @PostMapping("/listGroup")
    public Result listGroup(@RequestBody @ApiParam(name = "巡护人员分组列表", value = "json格式", required = true)
                                        Swagger<St4ScsCz> data,HttpServletRequest request) {
        St4ScsCz param = data.getData();

        if(!ObjectUtils.isNotNullAndEmpty(param.getPageNumber())&&!ObjectUtils.isNotNullAndEmpty(param.getPageSize())){
            return  Result.build(1001,"pageSize和pageNumber"+ResultMsg.MSG_1001);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        St4SysSa serUser = new St4SysSa();
        serUser.setSa001(Integer.valueOf(userId));
        return  iSt4ScsCzService.listGroup(param,serUser);
    }*/
    /**
     * 测试mybatisplus多表关联查询接口
     * @param data
     * @return
     *//*
    @PassToken
    @ApiOperation(value = "任务批次单个详情接口", notes = "此接口返回问题点批次数据", response = Result.class)
    @PostMapping("/plusList")
    public Result plusList(@RequestBody @ApiParam(name = "任务批次单个详情接口", value = "json格式", required = true) Swagger<St4ScsCl> data) {
        St4ScsCl param = data.getData();
        if(param.getCl001()==null){
            return  Result.build(1001,"任务批次CL001"+ResultMsg.MSG_1001);
        }
        param = iSt4ScsClService.getById(param.getCl001());
        Result res = new Result();
        res.setStatus(1000);
        res.setMsg("加载"+ResultMsg.MSG_1000);
        try{
            res.setData(BeanUtils.describe(param));
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(1003,ResultMsg.MSG_1003);
        }

        return  res;
    }*/

}
