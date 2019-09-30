package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.service.impl.ITQuestionVerificationService;
import com.gistone.util.DateUtils;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

/**
 * 人类活动问题核查处理表接口
 */
@RestController
@RequestMapping("/api/rlhd/questionVerification")
@Slf4j
@Transactional
public class TQuestionVerificationController {
    @Autowired(required = true)
    private ITQuestionVerificationService tQuestionVerificationService;

    /**
     * 问题核查处理表列表展示接口
     *
     * @param paramsMap
     * @return
     */
    @PostMapping(value = "/getQuestionList")
    public ResultVO QuestionVerificationList(@RequestBody Map<String, Object> paramsMap) throws ParseException {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        if(dataParam.get("pageNum")==null||dataParam.get("pageSize")==null){
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "pageNum,pageSize不能为空！");
        }
        Integer pageNum = Integer.valueOf(dataParam.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(dataParam.get("pageSize").toString());


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }


        String tqv_serival_number = (String) dataParam.get("tqv_serival_number");

        String addUser = (String) dataParam.get("addUser");

        String startDate = (String) dataParam.get("startDate");
        String endDate = (String) dataParam.get("endDate");


        Map<String, Object> result = tQuestionVerificationService.findAll(tqv_serival_number, addUser, DateUtils.getDateFormat(startDate), DateUtils.getDateFormat(endDate), pageNum, pageSize);




        return ResultVOUtil.success(result);

    }
}
