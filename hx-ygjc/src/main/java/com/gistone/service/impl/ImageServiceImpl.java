package com.gistone.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import com.gistone.entity.ShpBatch;
import com.gistone.mapper.ImageMapper;
import com.gistone.mapper.ShpBatchMapper;
import com.gistone.service.ImageService;
import com.gistone.util.HttpUtil;
import com.gistone.util.ResultEnum;
import com.gistone.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影像数据表 服务实现类
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-10-18
 */
@Service
@Transactional
@Slf4j
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper mapper;
    @Autowired
    private com.gistone.mapper.ShpBatchMapper shpBatchMapper;
    @Value("${IMAGE_EVA}")
    private String IMAGE_EVA;


    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, String userName) {
        QueryWrapper<Image> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            wrapper.like("name",userName);
        }
        wrapper.eq("del_flag",1);
        wrapper.orderByDesc("update_date");
        IPage<Image> imageIPage = mapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", imageIPage.getRecords());
        result.put("total", imageIPage.getTotal());
        return result;
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑
        for (Integer id : ids) {
            Image image = mapper.selectById(id);
            image.setDelFlag(0);
            mapper.updateById(image);
        }
    }


    @Override
    public void insert(String name, String url, Integer createBy,String remark) {
        //具体逻辑
        Image image = new Image().setName(name).setUrl(url).setCreateDate(LocalDateTime.now()).setCreateBy(createBy).setUpdateDate(new Date());
        if(StringUtils.isNotBlank(remark)){
           image.setRemark(remark);
       }
        mapper.insert(image);
    }


    @Override
    public void edit(Integer id,String name,String url,Integer  updateBy,String remark) {
        //具体逻辑
        Image image = mapper.selectById(id);
        image.setName(name).setUrl(url).setUpdateDate(new Date()).setUpdateBy(updateBy);
        if(StringUtils.isNotBlank(remark)){
            image.setRemark(remark);
        }
        mapper.updateById(image);
    }

    @Override
    public List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime) {
        List<Map<String, Object>> mapList = mapper.selectCount(code, currentTime, beforeTime);
        return mapList;
    }

    @Override
    public int getBeforeCount(String code, LocalDate beforeTime) {
        int sum = mapper.selectBeforeCount(code,beforeTime);
        return sum;
    }

    @Override
    public List<Map<String, Object>> getRlhdTotal() {

        //获取到最新数据的id
        int id = mapper.getLastDataId();
        //通过此id分组统计面积
        List<Map<String,Object>> result = mapper.getAreaGroupByType(id);
        return result;
    }

    @Override
    public ResultVO getAudit(Integer id) {
        JSONObject jSONObject = null;
        try {
            //获取影像SHP数据的FTP地址
            Image image = mapper.getImageById(id);
            String shpUrl = image.getShpurl();
            if(StringUtils.isBlank(shpUrl)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "未知影像数据");
            }
            //获取最新红线SHP数据的FTP地址
            ShpBatch newShpBatch = shpBatchMapper.getNewShpBatch();
            String ftpShpUrl = newShpBatch.getFtpShpUrl();
            if(StringUtils.isBlank(ftpShpUrl)){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "未知红线数据");
            }
            //发送请求
            String p1 = "?humanActivity="+shpUrl;
            String p2 = "&redline="+ftpShpUrl;
            String p3 = "&f=pjson";
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.GET(IMAGE_EVA+"/submitJob"+p1+p2+p3,null));
            String jobId = "/"+jsonObject.get("jobId");
            String res = HttpUtil.GET(IMAGE_EVA+"/jobs"+jobId,null);
            System.out.println(res);
            //判断是否成功




            //更新数据
            image.setContrastRed(res);
            int res2 = mapper.updateImage(image);
            if(0<res2){
                //返回数据
                jSONObject = new JSONObject();
                jSONObject.put("image",JSON.toJSONString(image, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat));




            }else{
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success(jSONObject);
    }

    @Override
    public ResultVO audit(Image image) {
        int res = mapper.updateImage(image);
        if(0<res){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "审核失败");
    }


}


