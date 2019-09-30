package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.TQuestionVerification;
import com.gistone.entity.TXunhuBiaozhu;
import com.gistone.entity.TXunhuBiaozhuPho;
import com.gistone.service.impl.ITQuestionVerificationService;
import com.gistone.service.impl.ITXunhuBiaozhuPhoService;
import com.gistone.service.impl.ITXunhuBiaozhuService;
import com.gistone.util.JsonObjectUtil;
import com.gistone.util.JsonToMapUtil;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * app端提交的统一接口
 */
@RestController
@RequestMapping("/api/rlhd/app")
@Slf4j
@Transactional
public class AppInterfaceController {
    //图片文件名称
    private String photoNames = "";
    //录音文件名称
    private String soundNames = "";

    @Autowired(required = true)
    private ITQuestionVerificationService tQuestionVerificationService;
    @Autowired(required = true)
    private ITXunhuBiaozhuService tXunhuBiaozhuService;
    @Autowired(required = true)
    private ITXunhuBiaozhuPhoService tXunhuBiaozhuPhoService;

    /**
     * 人类活动问题核查处理表添加接口
     *
     * @param paramsMap
     * @return
     */
    @PostMapping(value = "/insertQuestionVerification")
    public ResultVO insertQuestionVerification(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");

        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
//这里写的是航点信息的提交逻辑
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        int up_num = 0;
        try {
            HttpClient client = new HttpClient();
            // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
            //Object   json_biaozhu= URLDecoder.decode(dataParam.get("json_biaozhu").toString(),"UTF-8");//标注信息
            String xhryid = URLDecoder.decode(dataParam.get("xhryid").toString(), "UTF-8");//巡护人员id

            JSONArray form_json = JSONArray.fromObject(dataParam.get("json_biaozhu"));


            for (int i = 0; i < form_json.size(); i++) {

                JSONObject result = (JSONObject) form_json.get(i);

                //将json这的null字符串转化为null
                result = JsonObjectUtil.filterNull(result);
                TXunhuBiaozhu bz = new TXunhuBiaozhu();
                bz.setBzTijiao(result.get("bz_tijiao") == null ? "" : result.get("bz_tijiao").toString());//提交状态
                bz.setBzZhiwuNum(result.get("bz_zhiwu_num") == null ? "" : result.get("bz_zhiwu_num").toString());//植物数量
                bz.setBzLat(result.get("bz_lat") == null ? "" : result.get("bz_lat").toString());//纬度
                bz.setBzShengjing(result.get("bz_shengjing") == null ? "" : result.get("bz_shengjing").toString());//生境
                bz.setBzDongwu(result.get("bz_dongwu") == null ? "" : result.get("bz_dongwu").toString());//动物
                bz.setBzDongwuNianling(result.get("bz_dongwu_nianling") == null ? "" : result.get("bz_dongwu_nianling").toString());//动物年龄
                bz.setBzDongwuShuliang(result.get("bz_dongwu_shuliang") == null ? "" : result.get("bz_dongwu_shuliang").toString());//动物数量
                bz.setBzDongwuBeizhu(result.get("bz_dongwu_beizhu") == null ? "" : result.get("bz_dongwu_beizhu").toString());//动物备注
                bz.setBzHaiba(result.get("bz_haiba") == null ? "" : result.get("bz_haiba").toString());//海拔
                // bz.setBzOnlyPointGroup(bz_only_point_group == null ? "" : bz_only_point_group);//标注点分组，20190510添加，暂时没有
                bz.setBzXhryid(xhryid == null ? 0 : Integer.valueOf(xhryid));//巡护人员id
                //截取录音文件名称
                if (result.get("bz_luyin") != null && !"".equals(result.get("bz_luyin"))) {
                    //文件名称是以,分隔的
                    String array4[] = result.get("bz_luyin").toString().split(",");
                    String value = "";
                    for (int y = 0; y < array4.length; y++) {
                        if (y == array4.length - 1) {//最后一个文件名后面不需要加,
                            value += array4[y].toString().substring(array4[y].lastIndexOf("/") + 1).replace("\"", "");
                        } else {
                            value += array4[y].substring(array4[y].lastIndexOf("/")).replace("\"", "") + ",";
                        }
                    }
                    bz.setBzLuyin(value);
                } else {
                    bz.setBzLuyin(null);
                }

                bz.setBzTianqi(result.get("bz_tianqi") == null ? "" : result.get("bz_tianqi").toString());//天气
                bz.setBzLng(result.get("bz_lng") == null ? "" : result.get("bz_lng").toString());//纬度
                bz.setBzZhiwu(result.get("bz_zhiwu") == null ? "" : result.get("bz_zhiwu").toString());//植物
                bz.setBzBeizhu(result.get("bz_beizhu") == null ? "" : result.get("bz_beizhu").toString());//备注
                bz.setBzRenlei(result.get("bz_renlei") == null ? "" : result.get("bz_renlei").toString());//人类
                bz.setBzDidian(result.get("bz_didian") == null ? "" : result.get("bz_didian").toString());///地点
                bz.setBzDongwuHenji(result.get("bz_dongwu_henji") == null ? "" : result.get("bz_dongwu_henji").toString());//动物痕迹
                bz.setBzRenleiBeizhu(result.get("bz_renlei_beizhu") == null ? "" : result.get("bz_renlei_beizhu").toString());//人类备注
                bz.setBzDongwuXingbie(result.get("bz_dongwu_xingbie") == null ? "" : result.get("bz_dongwu_xingbie").toString());//动物性别
                bz.setBzDongwuBeizhu(result.get("bz_dongwu_beizhu") == null ? "" : result.get("bz_dongwu_beizhu").toString());//动物备注
                bz.setBzZhiwuBeizhu(result.get("bz_zhiwu_beizhu") == null ? "" : result.get("bz_zhiwu_beizhu").toString());//植物备注
                up_num = this.tXunhuBiaozhuService.insertSelective(bz); //添加标注信息
                Integer bzId = bz.getBzId();//得到刚插入的标注点的ID
                Object aa = result.get("bz_renlei_check");
                if (result.get("bz_renlei_check") != null && !StringUtils.isEmpty(result.get("bz_renlei_check").toString())) {
                    //将问题核查数据转化为json类型
                    JSONObject rcJson = JSONObject.fromObject(result.get("bz_renlei_check"));
                    //获取问题核查数据
                    TQuestionVerification tQuestionVerification = new TQuestionVerification();
                    String tqv_question = (String) rcJson.get("tqv_question");//问题

                    tQuestionVerification.setTqvQuestion(tqv_question);

                    String tqv_serival_number = (String) rcJson.get("tqv_serival_number");//编号

                    tQuestionVerification.setTqvSerivalNumber(tqv_serival_number);

                    String tqv_facility_name = rcJson.get("tqv_facility_name") == null ? "" : rcJson.get("tqv_facility_name").toString();//活动设施名称
                    /*if (StringUtils.isBlank(tqv_facility_name)) {
                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "活动设施名称不能为空");
                    }*/
                    tQuestionVerification.setTqvFacilityName(tqv_facility_name);
                    String tqv_facility_type = rcJson.get("tqv_facility_type") == null ? "" : rcJson.get("tqv_facility_type").toString();//活动设施类型
//                    if (StringUtils.isBlank(tqv_facility_type)) {
//                        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "活动设施类型不能为空");
//                    }
                    tQuestionVerification.setTqvFacilityType(tqv_facility_type);
                    String tqv_area = (String) rcJson.get("tqv_area");//面积

                    tQuestionVerification.setTqvArea(tqv_area);
                    String tqv_sectorization = (String) rcJson.get("tqv_sectorization");//所在功能区

                    tQuestionVerification.setTqvSectorization(tqv_sectorization);
                    String tqv_change_type = (String) rcJson.get("tqv_change_type");//变化类型

                    tQuestionVerification.setTqvChangeType(tqv_change_type);

                    String tqv_facility_status = (String) rcJson.get("tqv_facility_status");//活动设施现状

                    tQuestionVerification.setTqvFacilityStatus(tqv_facility_status);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String tqv_build_time = (String) rcJson.get("tqv_build_time");//建设时间
                    if (!StringUtils.isBlank(tqv_build_time)) {
                        try {
                            tQuestionVerification.setTqvBuildTime(sdf.parse(tqv_build_time));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    String tqv_formalities = (String) rcJson.get("tqv_formalities");//有无环评手续

                    tQuestionVerification.setTqvFormalities(tqv_formalities);

                    String tqv_proof = (String) rcJson.get("tqv_proof");//存在问题及主要生态影响

                    tQuestionVerification.setTqvProof(tqv_proof);
                    String tqv_influence = (String) rcJson.get("tqv_influence");//存在问题及主要生态影响

                    tQuestionVerification.setTqvInfluence(tqv_influence);
                    String tqv_result = (String) rcJson.get("tqv_result");//处理情况

                    tQuestionVerification.setTqvResult(tqv_result);

                    String tqv_check_unit = (String) rcJson.get("tqv_check_unit");//核查单位

                    tQuestionVerification.setTqvCheckUnit(tqv_check_unit);
                    String tqv_contact_way = (String) rcJson.get("tqv_contact_way");//联系方式

                    tQuestionVerification.setTqvContactWay(tqv_contact_way);
                    String tqv_check_time = (String) rcJson.get("tqv_check_time");//核查时间
                    if (!StringUtils.isBlank(tqv_check_time)) {
                        try {
                            tQuestionVerification.setTqvCheckTime(sdf.parse(tqv_check_time));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    String tqv_preparer = (String) rcJson.get("tqv_preparer");//填表人

                    tQuestionVerification.setTqvPreparer(tqv_preparer);
                    String tqv_check_people = (String) rcJson.get("tqv_check_people");//核查人

                    tQuestionVerification.setTqvCheckPeople(tqv_check_people);

                    String tqv_verifier = (String) rcJson.get("tqv_verifier");//审核人

                    tQuestionVerification.setTqvVerifier(tqv_verifier);
                    tQuestionVerification.setTqvBzId(bzId);
                    tQuestionVerification.setTqvAddTime(new Date());
                    int insertNum = tQuestionVerificationService.insertSelective(tQuestionVerification);
                    /**
                     * 目前问题表的数据并不是必填项所以不用管insertNum的值
                     */

                }

                if (bzId > 0) {
                    ResultVO resultVO = new ResultVO();
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.put("bzId", bzId);
                    resultVO.setData(map);
                    resultVO.setCode("0000");
                    resultVO.setMsg("添加成功!");
                    return resultVO;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "上传失败");


    }


    /**
     * //图片录音的上传接口
     *
     * @param file      圖片
     * @param soundFile 錄音
     * @param data      航點的ID
     * @return
     */
    @PostMapping(value = "/uploadPho")
    public ResultVO add(MultipartFile[] file, MultipartFile[] soundFile, @RequestParam("data") String data) {
        //请求参数格式校验
        if (StringUtils.isBlank(data)) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "data不能为空");
        }
        JSONObject a = JSONObject.fromObject(data);
        Map<String, Object> map = JsonToMapUtil.parseJSON2Map(a);
        Map<String, Object> dataParam = (Map<String, Object>) map.get("data");
        if (dataParam.get("bzId") == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "航点ID不能为空");
        }
        Long bzId = Long.valueOf(dataParam.get("bzId").toString());
        // 图片上传
        if (file != null && file.length > 0) {
            //如果图片不为空，则把图片存到
            for (MultipartFile file1 : file) {

                //获取到图片名称
                String originalFilename = file1.getOriginalFilename();

                //1.上传图片返回地址
                String path = tXunhuBiaozhuPhoService.uploadPicture(file1);
                //去掉盘符前缀
                String rePath = null;
                if (path.length() > 3) {
                    rePath = path.substring(2);
                }
                TXunhuBiaozhuPho pho = new TXunhuBiaozhuPho();
                pho.setPhoUrl(rePath);
                pho.setPhoBzId(bzId);
                //2.把图片关联入库
                Integer ins = tXunhuBiaozhuPhoService.SavePhoMsg(pho);
                if (ins < 1) {
                    ResultVOUtil.error(ResultEnum.ERROR.getCode(), "上传失败");
                }
            }
        }
        //录音
        if (soundFile != null && soundFile.length > 0) {
            //如果图片不为空，则把图片存到
            for (MultipartFile file1 : soundFile) {

                //获取到图片名称
                String originalFilename = file1.getOriginalFilename();

                //1.上传图片返回地址
                String path = tXunhuBiaozhuPhoService.uploadPicture(file1);
                //去掉盘符前缀
                String rePath = null;
                if (path.length() > 3) {
                    rePath = path.substring(2);
                }
                TXunhuBiaozhuPho pho = new TXunhuBiaozhuPho();
                pho.setPhoUrl(rePath);
                pho.setPhoBzId(bzId);
                //2.把图片关联入库
                Integer ins = tXunhuBiaozhuPhoService.SavePhoMsg(pho);
                if (ins < 1) {
                    ResultVOUtil.error(ResultEnum.ERROR.getCode(), "上传失败");
                }
            }
        }

        return ResultVOUtil.success();
    }


}
