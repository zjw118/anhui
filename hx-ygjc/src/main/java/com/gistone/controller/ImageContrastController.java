package com.gistone.controller;

import com.gistone.entity.ImageContrast;
import com.gistone.entity.SysUser;
import com.gistone.service.ImageContrastService;
import com.gistone.util.PageBean;
import com.gistone.util.Result;
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
    public Result add(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) throws Exception {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return Result.build(2,"需要包含data");
        if(null==params.get("image1Id"))return Result.build(2,"image1Id不能为空");
        if(null==params.get("image2Id"))return Result.build(2,"image2Id不能为空");
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
    public Result list(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params == null) return Result.build(2,"需要包含data");
        if(null==params.get("pageIndex"))return Result.build(2,"pageIndex不能为空");
        if(null==params.get("pageSize"))return Result.build(2,"pageSize不能为空");
        PageBean pageBean = new PageBean();
        pageBean.setPageIndex(Integer.valueOf(params.get("pageIndex")+""));
        pageBean.setPageSize(Integer.valueOf(params.get("pageSize")+""));
        pageBean.setStr1(params.get("name")+"");
        return imageContrastService.list(pageBean);
    }

    /**
     * 删除
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/delete")
    public Result delete(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params==null) return Result.build(2,"需要包含data");
        if (null==params.get("id")) return Result.build(2,"id不能为空");
        return imageContrastService.delete(Integer.valueOf(params.get("id")+""));
    }







}
