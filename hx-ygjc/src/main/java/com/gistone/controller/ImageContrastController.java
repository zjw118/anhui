package com.gistone.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ygjc/imageContrast")
public class ImageContrastController {

//    @Autowired
//    private IterpretationService service;
//    @PostMapping("/list")
//    public ResultVO getList(@RequestBody Map<String, Object> paramsMap) {
//        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
//        if (params == null) {
//            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "请求数据data不能为空！");
//        }
//        Integer id = (Integer) params.get("id");
//        Map<String, Object> result = service.list(1, 99999999, id);
//        return ResultVOUtil.success(result);
//    }



}
