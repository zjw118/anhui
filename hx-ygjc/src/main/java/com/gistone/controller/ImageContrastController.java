package com.gistone.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.entity.SysUser;
import com.gistone.service.ImageContrastService;
import com.gistone.service.ImageService;
import com.gistone.util.PageBean;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import com.gistone.util.ShpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ygjc/imageContrast")
public class ImageContrastController {
    @Autowired
    private ImageContrastService imageContrastService;
    @Autowired
    private ImageService imageService;
    @Value("${PATH}")
    private String PATH;



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
        if(null!=params.get("name"))
        imageContrast.setName(params.get("name")+"");
        if(null!=params.get("remark"))
        imageContrast.setRemark(params.get("remark")+"");
        imageContrast.setDate(new Date());
        if(null!=user) imageContrast.setUserId(user.getId());
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
     * 获取
     * @param request
     * @param paramsMap
     * @return
     */
    @PostMapping("/get")
    public ResultVO get(HttpServletRequest request, @RequestBody Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("data");
        if (params==null) return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"data结构");
        if (null==params.get("id"))return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"id不能为空");

        //详情
        ImageContrast imageContrast = imageContrastService.getById(Integer.valueOf(params.get("id")+""));
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",imageContrast.getImage1Id());
        Image entity1 = imageService.getOne(queryWrapper);
        QueryWrapper<Image> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id",imageContrast.getImage2Id());
        Image entity2 = imageService.getOne(queryWrapper2);


        //数据变化对比
        JSONArray jsonArray1 = JSONArray.fromObject(imageContrast.getData1());
        JSONArray jsonArray2 = JSONArray.fromObject(imageContrast.getData2());


        int area = 0;
        int Shape_area = 0;
        int area_1 = 0;
        int Join_Count = 0;
        int area_12 = 0;
        int FID__file2 = 0;
        int FID__file1 = 0;
        int zhoumianbi = 0;
        int center_12 = 0;
        int Shape_leng = 0;


        for (Object o : jsonArray1) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            Object attributes = jsonObject.get("attributes");
            JSONObject data = JSONObject.fromObject(attributes);

            area += Integer.valueOf(data.get("area")+"");
            Shape_area += Integer.valueOf(data.get("Shape_area")+"");
            area_1 += Integer.valueOf(data.get("area_1")+"");
            Join_Count += Integer.valueOf(data.get("Join_Count")+"");
            area_12 += Integer.valueOf(data.get("area_12")+"");
            FID__file2 += Integer.valueOf(data.get("FID__file2")+"");
            FID__file1 += Integer.valueOf(data.get("FID__file1")+"");
            zhoumianbi += Integer.valueOf(data.get("zhoumianbi")+"");
            center_12 += Integer.valueOf(data.get("center_12")+"");
            Shape_leng += Integer.valueOf(data.get("Shape_leng")+"");

        }
        for (Object o : jsonArray2) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            Object attributes = jsonObject.get("attributes");
            JSONObject data = JSONObject.fromObject(attributes);

        }






        Map map = new HashMap();
        map.put("imageContrast",imageContrast);
        map.put("image1",entity1);
        map.put("image2",entity2);




        return ResultVOUtil.success(map);
    }







}
