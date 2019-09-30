package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.TXunhuBiaozhu;
import com.gistone.service.impl.ITXunhuBiaozhuService;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rlhd/mark")
@Slf4j
public class TXunhuBiaozhuController {
    @Autowired
    private ITXunhuBiaozhuService tXunhuBiaozhuService;

    @PostMapping(value = "/getMarkList")
    public ResultVO QuestionVerificationList(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }

        Integer pageNum = Integer.valueOf(dataParam.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(dataParam.get("pageSize").toString());


        if (pageNum == null) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        String bzShengjing = "";
        if (dataParam.get("bzShengjing") != null) { //生境（目前只模糊查询了“bzShengjing”这个字段）
            bzShengjing = (String) dataParam.get("bzShengjing");
        }
        Map<String, Object> result = tXunhuBiaozhuService.findAll(pageNum, pageSize, bzShengjing);
        return ResultVOUtil.success(result);
    }

    /**
     * 通过航点的id拿到航点的详情
     *
     * @param paramsMap
     * @return
     */
    @PostMapping(value = "/getMarkDetailById")
    public ResultVO getMarkDetailById(@RequestBody Map<String, Object> paramsMap) {
        //请求参数格式校验
        Map<String, Object> dataParam = (Map<String, Object>) paramsMap.get("data");
        if (dataParam == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
        }
        String markId = "";//航点ID
        if (dataParam.get("markId") == null) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "航点markId不能为空！");
        } else {
            markId = dataParam.get("markId").toString();
            return ResultVOUtil.success(tXunhuBiaozhuService.selectByPrimaryKey(Integer.valueOf(markId)));
        }

    }

    /**
     * @param
     * @return com.gistone.VO.ResultVO
     * @description:查询所有的航点列表
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/9/4 0004 17:56
     */
    @PostMapping("/allList")
    public ResultVO allList() {
        List<TXunhuBiaozhu> list = tXunhuBiaozhuService.list(null);
        return ResultVOUtil.success(list);
    }
}
