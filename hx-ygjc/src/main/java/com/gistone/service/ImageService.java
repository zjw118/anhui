package com.gistone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.Image;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
* <p>
* 影像数据表 服务类
* </p>
*
* @author zf1017@foxmail.com
* @since 2019-10-18
*/
public interface ImageService extends IService<Image> {

    Map<String, Object> list(Integer pageNum, Integer pageSize, String Name);

    void delete(List<Integer> id);

    void insert(String name,  String url,Integer createBy,String remark,String date);

    void edit(Integer id,String name,String url,Integer updateBy,String remark);

    List<Map<String, Object>> getCount(String code, LocalDate currentTime, LocalDate beforeTime);

    int getBeforeCount(String code,LocalDate beforeTime);

    List<Map<String, Object>> getRlhdTotal();

    List<Map<String, Object>> getCountGroupByType(Integer imageId);

    List<Map<String, Object>> getAreaGroupByType(Integer imageId);

    List<Map<String, Object>> getCountChange();

    List<Map<String, Object>> getAreaChange();

    ResultVO audit(Image image);

    ResultVO gdShp(double rc);

    ResultVO gdShp2(Object data);

    ResultVO getGdFile();

    ResultVO exportZTTJ(String data1,String data2,String data3);

    ResultVO gdFile(HttpServletResponse response);

    ResultVO getAudit(Integer id);
    ResultVO addAudit(Integer id, JSONObject json);

    ResultVO getAudit2(Integer id);
    ResultVO addAudit2(Integer id, JSONObject json);

    ResultVO downImageShp(Integer id);

    List<Map<String, Object>> totalByType(Integer type);


}

