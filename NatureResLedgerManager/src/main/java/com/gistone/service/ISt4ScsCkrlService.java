package com.gistone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4ScsCkrl;
import com.gistone.entity.St4SysSa;
import com.gistone.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjw
 * @since 2019-11-07
 */
public interface ISt4ScsCkrlService extends IService<St4ScsCkrl> {
    Result listHumanStage(St4ScsCkrl ckrl);
    Result importHumanStage(Map<String, MultipartFile> map, St4SysSa seUser);

    ResultVO importExcel(String path) throws Exception;

    /**
     * 导出人类活动台账excel
     * @param ckrl
     * @return
     */
    ResultVO exportHumanStage(St4ScsCkrl ckrl, HttpServletResponse response);

}
