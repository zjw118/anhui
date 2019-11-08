package com.gistone.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ImageContrast;
import com.gistone.entity.SysUser;
import com.gistone.mapper.ImageContrastMapper;
import com.gistone.service.ImageContrastService;
import com.gistone.service.ImageService;
import com.gistone.util.PageBean;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
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
import java.util.Map;

@RestController
@RequestMapping("/api/ygjc/imageContrast")
public class ImageContrastController {
    @Autowired
    private ImageContrastService imageContrastService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageContrastMapper imageContrastMapper;
    @Value("${PATH}")
    private String PATH;



    /**
     * 添加影像对比
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
        ImageContrast ic = new ImageContrast();
        ic.setId(Integer.valueOf(params.get("id")+""));
        ImageContrast imageContrast = imageContrastMapper.getImageContrast(ic);
//        ImageContrast imageContrast = imageContrastService.getById(Integer.valueOf(params.get("id")+""));
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",imageContrast.getImage1Id());
        Image entity1 = imageService.getOne(queryWrapper);
        QueryWrapper<Image> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id",imageContrast.getImage2Id());
        Image entity2 = imageService.getOne(queryWrapper2);

        String shp1 = entity1.getShp();
        String shp2 = entity2.getShp();


        int nyyd = 0;
        int jmd = 0;
        int gkyd = 0;
        int csc = 0;
        int nyss = 0;
        int lyss = 0;
        int jtss = 0;
        int yzc = 0;
        int dl = 0;
        int qt = 0;
        JSONArray jsonArray = JSONArray.fromObject(shp1);
        for (Object ja : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);

            if(null!=jsonObject1.get("area")){
                if("农业用地".equals(jsonObject1.get("type")))
                    nyyd+= Double.valueOf(jsonObject1.get("area")+"");
                if("居民点".equals(jsonObject1.get("type")))
                    jmd+= Double.valueOf(jsonObject1.get("area")+"");
                if("工矿用地".equals(jsonObject1.get("type")))
                    gkyd+= Double.valueOf(jsonObject1.get("area")+"");
                if("采石场".equals(jsonObject1.get("type")))
                    csc+= Double.valueOf(jsonObject1.get("area")+"");
                if("能源设施".equals(jsonObject1.get("type")))
                    nyss+= Double.valueOf(jsonObject1.get("area")+"");
                if("旅游设施".equals(jsonObject1.get("type")))
                    lyss+= Double.valueOf(jsonObject1.get("area")+"");
                if("交通设施".equals(jsonObject1.get("type")))
                    jtss+= Double.valueOf(jsonObject1.get("area")+"");
                if("养殖场".equals(jsonObject1.get("type")))
                    yzc+= Double.valueOf(jsonObject1.get("area")+"");
                if("道路".equals(jsonObject1.get("type")))
                    dl+= Double.valueOf(jsonObject1.get("area")+"");
                if("其他人工设施".equals(jsonObject1.get("type")))
                    qt+= Double.valueOf(jsonObject1.get("area")+"");
            }
        }
        
        
        int nyyd2 = 0;
        int jmd2 = 0;
        int gkyd2 = 0;
        int csc2 = 0;
        int nyss2 = 0;
        int lyss2 = 0;
        int jtss2 = 0;
        int yzc2 = 0;
        int dl2 = 0;
        int qt2 = 0;
        JSONArray jsonArray2 = JSONArray.fromObject(shp2);
        for (Object ja : jsonArray2) {
            JSONObject jsonObject = JSONObject.fromObject(ja);
            Object attributes = jsonObject.get("attributes");
            JSONObject jsonObject1 = JSONObject.fromObject(attributes);

            if(null!=jsonObject1.get("area")){
                if("农业用地".equals(jsonObject1.get("type")))
                    nyyd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("居民点".equals(jsonObject1.get("type")))
                    jmd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("工矿用地".equals(jsonObject1.get("type")))
                    gkyd2+= Double.valueOf(jsonObject1.get("area")+"");
                if("采石场".equals(jsonObject1.get("type")))
                    csc2+= Double.valueOf(jsonObject1.get("area")+"");
                if("能源设施".equals(jsonObject1.get("type")))
                    nyss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("旅游设施".equals(jsonObject1.get("type")))
                    lyss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("交通设施".equals(jsonObject1.get("type")))
                    jtss2+= Double.valueOf(jsonObject1.get("area")+"");
                if("养殖场".equals(jsonObject1.get("type")))
                    yzc2+= Double.valueOf(jsonObject1.get("area")+"");
                if("道路".equals(jsonObject1.get("type")))
                    dl2+= Double.valueOf(jsonObject1.get("area")+"");
                if("其他人工设施".equals(jsonObject1.get("type")))
                    qt2+= Double.valueOf(jsonObject1.get("area")+"");
            }
        }

        int nyyd3 = nyyd2-nyyd;
        int jmd3 = jmd2-jmd;
        int gkyd3 = gkyd2-gkyd;
        int csc3 = csc2-csc;
        int nyss3 = nyss2-nyss;
        int lyss3 = lyss2-lyss;
        int jtss3 = jtss2-jtss;
        int yzc3 = yzc2-yzc;
        int dl3 = dl2-dl;
        int qt3 = qt2-qt;


        Map map = new HashMap();
        map.put("imageContrast",imageContrast);
        map.put("image1",entity1);
        map.put("image2",entity2);
        
        map.put("nyyd1",nyyd);
        map.put("jmd1",jmd);
        map.put("gkyd1",gkyd);
        map.put("csc1",csc);
        map.put("nyss1",nyss);
        map.put("lyss1",lyss);
        map.put("jtss1",jtss);
        map.put("yzc1",yzc);
        map.put("dl1",dl);
        map.put("qt1",qt);
        
        map.put("nyyd2",nyyd2);
        map.put("jmd2",jmd2);
        map.put("gkyd2",gkyd2);
        map.put("csc2",csc2);
        map.put("nyss2",nyss2);
        map.put("lyss2",lyss2);
        map.put("jtss2",jtss2);
        map.put("yzc2",yzc2);
        map.put("dl2",dl2);
        map.put("qt2",qt2);
        
        map.put("nyyd3",nyyd3);
        map.put("jmd3",jmd3);
        map.put("gkyd3",gkyd3);
        map.put("csc3",csc3);
        map.put("nyss3",nyss3);
        map.put("lyss3",lyss3);
        map.put("jtss3",jtss3);
        map.put("yzc3",yzc3);
        map.put("dl3",dl3);
        map.put("qt3",qt3);

        return ResultVOUtil.success(map);
    }







}
