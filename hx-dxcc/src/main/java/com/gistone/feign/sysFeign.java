package com.gistone.feign;

import com.gistone.VO.VersionVO;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/22 0022 14:52
 * @description
 */
public interface sysFeign {
    @RequestMapping(value = "/sysDbVersion/updateFile")
   VersionVO getDbFile();

    @RequestMapping(value = "/sysShapeVersion/updateFile")
    List<VersionVO> getShapeFile();
}
