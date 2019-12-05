package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.LsRedlineinfo;
import com.gistone.entity.LsRedlineinfoProcess;
import com.gistone.entity.LsRedlineinfoTemplate;
import com.gistone.entity.LsRedlineinfoVersion;
import com.gistone.mapper.LsRedlineinfoMapper;
import com.gistone.service.LsRedlineinfoService;
import com.gistone.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * 红线信息服务-临时
 */
@RestController
@RequestMapping("/api/ygjc/redlineinfo")
public class LsRedlineinfoController {

    @Autowired
    private LsRedlineinfoService LsRedlineinfoService;
    @Autowired
    private LsRedlineinfoMapper lsRedlineinfoMapper;

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
    @Value("${PATH}")
    private String PATH;



    //    流程-增
    @PostMapping("/processInsert")
    public ResultVO processInsert(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
                if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");

            LsRedlineinfoProcess LsRedlineinfoProcess = new LsRedlineinfoProcess();
            if (null!=params.get("name"))
                LsRedlineinfoProcess.setName(params.get("name").toString());
            if (null!=params.get("audit"))
                LsRedlineinfoProcess.setAudit(Integer.valueOf(params.get("audit").toString()));
            if (null!=params.get("remark"))
                LsRedlineinfoProcess.setRemark(params.get("remark").toString());
            LsRedlineinfoProcess.setUpdatetime(new Date());
            return LsRedlineinfoService.processInsert(LsRedlineinfoProcess);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    流程-删
    @PostMapping("/processDelete")
    public ResultVO processDelete(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoProcess LsRedlineinfoProcess = new LsRedlineinfoProcess();
            LsRedlineinfoProcess.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.processDelete(LsRedlineinfoProcess);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    流程-改
    @PostMapping("/processUpdate")
    public ResultVO processUpdate(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoProcess LsRedlineinfoProcess = new LsRedlineinfoProcess();
            if (null!=params.get("name"))
                LsRedlineinfoProcess.setName(params.get("name").toString());
            if (null!=params.get("audit"))
                LsRedlineinfoProcess.setAudit(Integer.valueOf(params.get("audit").toString()));
            if (null!=params.get("remark"))
                LsRedlineinfoProcess.setRemark(params.get("remark").toString());
            LsRedlineinfoProcess.setUpdatetime(new Date());
            LsRedlineinfoProcess.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.processUpdate(LsRedlineinfoProcess);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    流程-分页
    @PostMapping("/processSelect")
    public ResultVO processSelect(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");

            Object pageIndex = params.get("pageIndex");
            if (pageIndex==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"当前页不可为空");
            Object pageSize = params.get("pageSize");
            if (pageSize==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"每页条数不可为空");

            PageBean pageBean = new PageBean();
            if(null!=params.get("name"))
                pageBean.setStr1(params.get("name").toString());
            pageBean.setPageIndex(Integer.valueOf(pageIndex.toString()));
            pageBean.setPageSize(Integer.valueOf(pageSize.toString()));
            pageBean.setPoSum(lsRedlineinfoMapper.getPoSumProcess(pageBean));
            pageBean.setPoList(lsRedlineinfoMapper.selectPoListProcess(pageBean));

            return ResultVOUtil.success(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }



    @PostMapping("/processList")
    public ResultVO processList() {
        return ResultVOUtil.success(lsRedlineinfoMapper.LsRedlineinfoProcessList());
    }

//--------------------------------------------------------------------

    //    模板-增
    @PostMapping("/templateInsert")
    public ResultVO templateInsert(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            LsRedlineinfoTemplate LsRedlineinfoTemplate = new LsRedlineinfoTemplate();
            if (null!=params.get("compass"))
                LsRedlineinfoTemplate.setCompass(Integer.valueOf(params.get("compass").toString()));
            if (null!=params.get("scale"))
                LsRedlineinfoTemplate.setScale(Integer.valueOf(params.get("scale").toString()));
            if (null!=params.get("name"))
                LsRedlineinfoTemplate.setName(params.get("name").toString());
            LsRedlineinfoTemplate.setUpdatetime(new Date());
            return LsRedlineinfoService.templateInsert(LsRedlineinfoTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    模板-删
    @PostMapping("/templateDelete")
    public ResultVO templateDelete(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoTemplate LsRedlineinfoTemplate = new LsRedlineinfoTemplate();
            LsRedlineinfoTemplate.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.templateDelete(LsRedlineinfoTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    模板-改
    @PostMapping("/templateUpdate")
    public ResultVO templateUpdate(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoTemplate LsRedlineinfoTemplate = new LsRedlineinfoTemplate();
            if (null!=params.get("compass"))
                LsRedlineinfoTemplate.setCompass(Integer.valueOf(params.get("compass").toString()));
            if (null!=params.get("scale"))
                LsRedlineinfoTemplate.setScale(Integer.valueOf(params.get("scale").toString()));
            if (null!=params.get("name"))
                LsRedlineinfoTemplate.setName(params.get("name").toString());
            LsRedlineinfoTemplate.setUpdatetime(new Date());
            LsRedlineinfoTemplate.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.templateUpdate(LsRedlineinfoTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    模板-分页
    @PostMapping("/templateSelect")
    public ResultVO templateSelect(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");

            Object pageIndex = params.get("pageIndex");
            if (pageIndex==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"当前页不可为空");
            Object pageSize = params.get("pageSize");
            if (pageSize==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"每页条数不可为空");

            PageBean pageBean = new PageBean();
            if(null!=params.get("name"))
                pageBean.setStr1(params.get("name").toString());
            pageBean.setPageIndex(Integer.valueOf(pageIndex.toString()));
            pageBean.setPageSize(Integer.valueOf(pageSize.toString()));
            pageBean.setPoSum(lsRedlineinfoMapper.getPoSumTemplate(pageBean));
            pageBean.setPoList(lsRedlineinfoMapper.selectPoListTemplate(pageBean));

            return ResultVOUtil.success(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    @PostMapping("/templateList")
    public ResultVO templateList() {
        return ResultVOUtil.success(lsRedlineinfoMapper.LsRedlineinfoTemplateList());
    }



//--------------------------------------------------------------------

    //    版本-增
    @PostMapping("/versionInsert")
    public ResultVO versionInsert(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            LsRedlineinfoVersion LsRedlineinfoVersion = new LsRedlineinfoVersion();
            if(null!=params.get("process_id"))
                LsRedlineinfoVersion.setProcess_id(Integer.valueOf(params.get("process_id").toString()));
            if(null!=params.get("template_id"))
                LsRedlineinfoVersion.setTemplate_id(Integer.valueOf(params.get("template_id").toString()));
            if(null!=params.get("name"))
                LsRedlineinfoVersion.setName(params.get("name").toString());
            LsRedlineinfoVersion.setUpdatetime(new Date());
            return LsRedlineinfoService.versionInsert(LsRedlineinfoVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    版本-删
    @PostMapping("/versionDelete")
    public ResultVO versionDelete(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoVersion LsRedlineinfoVersion = new LsRedlineinfoVersion();
            LsRedlineinfoVersion.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.versionDelete(LsRedlineinfoVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    版本-改
    @PostMapping("/versionUpdate")
    public ResultVO versionUpdate(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfoVersion LsRedlineinfoVersion = new LsRedlineinfoVersion();
            if(null!=params.get("process_id"))
                LsRedlineinfoVersion.setProcess_id(Integer.valueOf(params.get("process_id").toString()));
            if(null!=params.get("template_id"))
                LsRedlineinfoVersion.setTemplate_id(Integer.valueOf(params.get("template_id").toString()));
            if(null!=params.get("name"))
                LsRedlineinfoVersion.setName(params.get("name").toString());
            LsRedlineinfoVersion.setUpdatetime(new Date());
            LsRedlineinfoVersion.setId(Integer.valueOf(id.toString()));
             return LsRedlineinfoService.versionUpdate(LsRedlineinfoVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    版本-分页
    @PostMapping("/versionSelect")
    public ResultVO versionSelect(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");

            Object pageIndex = params.get("pageIndex");
            if (pageIndex==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"当前页不可为空");
            Object pageSize = params.get("pageSize");
            if (pageSize==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"每页条数不可为空");

            PageBean pageBean = new PageBean();
            if(null!=params.get("name"))
                pageBean.setStr1(params.get("name").toString());
            pageBean.setPageIndex(Integer.valueOf(pageIndex.toString()));
            pageBean.setPageSize(Integer.valueOf(pageSize.toString()));
            pageBean.setPoSum(lsRedlineinfoMapper.getPoSumVersion(pageBean));
            pageBean.setPoList(lsRedlineinfoMapper.selectPoListVersion(pageBean));
            return ResultVOUtil.success(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }

    @PostMapping("/versionList")
    public ResultVO versionList() {
        return ResultVOUtil.success(lsRedlineinfoMapper.LsRedlineinfoVersionList());
    }

    //--------------------------------------------------------------------


    //    服务-增
    @PostMapping("/infoInsert")
    public ResultVO infoInsert(HttpServletRequest request,LsRedlineinfo lsRedlineinfo) {
        try {
            //上传附件
            String path = PATH+"/epr/lsRedlineinfo/";//本地路径
            String[] arr = {"zip"};
            Map<String, String> stringStringMap = FileUtil.uploadFile(request, path, arr, 500 * 1000000l);//1T

            String name = "";//本地新附件名称
            if(null!=stringStringMap){
                String error = stringStringMap.get("error");
                if(StringUtils.isNotBlank(error)){
                    return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), error);
                }
                String newName = stringStringMap.get("newName");
                if(StringUtils.isNotBlank(newName)){
                    name = newName;
                }
            }
            //上传FTP
            String ftpPath = "/lsRedlineinfo/";
            String fileName = name;
            FileInputStream input = new FileInputStream(new File(PATH+"/epr/lsRedlineinfo/"+name));
            String res = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
            if("0".equals(res)){
                lsRedlineinfo.setUpdatetime(new Date());
                lsRedlineinfo.setFtp_shp("E:/FTP/lsRedlineinfo/"+fileName);
                //判断是否需要审核
                int audit = lsRedlineinfoMapper.getAudit(lsRedlineinfo.getVersion_id());
                lsRedlineinfo.setAudit(audit);
                return LsRedlineinfoService.infoInsert(lsRedlineinfo);
            }

             return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    服务-删
    @PostMapping("/infoDelete")
    public ResultVO infoDelete(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            LsRedlineinfo LsRedlineinfo = new LsRedlineinfo();
            LsRedlineinfo.setId(Integer.valueOf(id.toString()));
            return LsRedlineinfoService.infoDelete(LsRedlineinfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    服务-改
    @PostMapping("/infoUpdate")
    public ResultVO infoUpdate(HttpServletRequest request,LsRedlineinfo lsRedlineinfo) {
        try {
            //上传附件
            String path = PATH+"/epr/lsRedlineinfo/";//本地路径
            String[] arr = {"zip"};
            Map<String, String> stringStringMap = FileUtil.uploadFile(request, path, arr, 1024 * 1000000l);//1T

            String name = "";//本地新附件名称
            if(null!=stringStringMap){
                String error = stringStringMap.get("error");
                if(StringUtils.isNotBlank(error)){
                    return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), error);
                }
                String newName = stringStringMap.get("newName");
                if(StringUtils.isNotBlank(newName)){
                    name = newName;
                }
                //上传FTP
                String ftpPath = "/lsRedlineinfo/";
                String fileName = name;
                FileInputStream input = new FileInputStream(new File(PATH+"/epr/lsRedlineinfo/"+name));
                String res = FTPUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
                if("0".equals(res)){
                    lsRedlineinfo.setUpdatetime(new Date());
                    lsRedlineinfo.setFtp_shp("E:\\FTP\\lsRedlineinfo\\"+fileName);
                    return LsRedlineinfoService.infoUpdate(lsRedlineinfo);
                }
            }

            lsRedlineinfo.setUpdatetime(new Date());
            return LsRedlineinfoService.infoUpdate(lsRedlineinfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }
    //    服务-分页
    @PostMapping("/infoSelect")
    public ResultVO infoSelect(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");

            Object pageIndex = params.get("pageIndex");
            if (pageIndex==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"当前页不可为空");
            Object pageSize = params.get("pageSize");
            if (pageSize==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"每页条数不可为空");

            PageBean pageBean = new PageBean();
            if(null!=params.get("name"))
                pageBean.setStr1(params.get("name").toString());
            pageBean.setPageIndex(Integer.valueOf(pageIndex.toString()));
            pageBean.setPageSize(Integer.valueOf(pageSize.toString()));
            pageBean.setPoSum(lsRedlineinfoMapper.getPoSumInfo(pageBean));
            pageBean.setPoList(lsRedlineinfoMapper.selectPoListInfo(pageBean));

            return ResultVOUtil.success(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }



    /**
     * 审核
     * 审核失败发送邮件通知
     * anhuihongxian@163.com
     * 邮箱密码 ahhx123
     * smtp授权密码 ahhx1234
     * @param paramsMap
     * @return
     */
    @PostMapping("/audit")
    public ResultVO audit(@RequestBody Map<String, Object> paramsMap) {
        try {
            Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
            if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请使用data包含");
            Object id = params.get("id");
            if (id==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不可为空");
            Object audit = params.get("audit");
            if (audit==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"audit不可为空");

            //如果审核失败发送邮件
            if(3==Integer.valueOf(audit.toString())){
                Object email = params.get("email");
                if (email==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"email不可为空");
                String mailFrom = "anhuihongxian@163.com";
                String password_mailFrom = "ahhx1234";
                String mailTo = email.toString();
                String mailTittle="红线审核失败";
                String mailText = "您提交的红线服务审核失败，请检验后重新提交。";
                String mail_host="smtp.163.com";
                boolean b = EmailUtil.sendMail(mailFrom, password_mailFrom, mailTo, mailTittle, mailText, mail_host);
//                System.out.println("发送邮件="+b);
            }

            LsRedlineinfo LsRedlineinfo = new LsRedlineinfo();
            LsRedlineinfo.setId(Integer.valueOf(id.toString()));
            LsRedlineinfo.setUpdatetime(new Date());
            LsRedlineinfo.setAudit(Integer.valueOf(audit.toString()));
            return LsRedlineinfoService.infoUpdate(LsRedlineinfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求异常");
        }
    }









}
