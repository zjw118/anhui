package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.ImageContrast;
import com.gistone.entity.SysUser;
import com.gistone.service.ImageContrastService;
import com.gistone.util.PageBean;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/ygjc/imageContrast")
public class ImageContrastController {
    @Autowired
    private ImageContrastService imageContrastService;
    /**
     * 添加对比
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/add")
    public ResultVO add(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) throws Exception {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if(null==params.get("image1Id"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"image1Id不能为空");
        if(null==params.get("image2Id"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"image2Id不能为空");
        Integer id1 = Integer.valueOf(params.get("image1Id")+"");
        Integer id2 = Integer.valueOf(params.get("image2Id")+"");

        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        ImageContrast imageContrast = new ImageContrast();
        imageContrast.setImage1Id(id1);
        imageContrast.setImage2Id(id2);
        imageContrast.setUserId(user.getId());
        imageContrast.setName(params.get("name")+"");
        imageContrast.setRemark(params.get("remark")+"");
        return imageContrastService.add(imageContrast);
    }

    /**
     * 分页查询
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/list")
    public ResultVO list(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if(null==params.get("pageNum"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageNum不能为空");
        if(null==params.get("pageSize"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"pageSize不能为空");
        PageBean pageBean = new PageBean();
        pageBean.setPageIndex(Integer.valueOf(params.get("pageNum")+""));
        pageBean.setPageSize(Integer.valueOf(params.get("pageSize")+""));
        pageBean.setStr1(params.get("name")+"");
        return imageContrastService.list(pageBean);
    }
    @PostMapping("/like")
    public ResultVO like(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
//        if(null==params.get("name"))return null;
        return imageContrastService.like(params.get("name")==null?"":params.get("name")+"");
    }




    /**
     * 删除
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/delete")
    public ResultVO delete(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if (null==params.get("id"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        return imageContrastService.delete(Integer.valueOf(params.get("id")+""));
    }


    /**
     * 删除
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/get")
    public ResultVO get(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if (null==params.get("id"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");
        return imageContrastService.get(Integer.valueOf(params.get("id")+""));
    }







}
