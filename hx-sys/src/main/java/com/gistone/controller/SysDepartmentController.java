package com.gistone.controller;


import com.gistone.VO.ResultVO;
import com.gistone.entity.SysDepartment;
import com.gistone.service.ISysDepartmentService;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zf1017@foxmail.com
 * @since 2019-04-22
 */
@RestController
@RequestMapping("/api/sys/sysDepartment")
public class SysDepartmentController {
    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @RequestMapping(value = "/getDepartmentList",method = RequestMethod.POST)
    public ResultVO getDepartmentList(){
        List<SysDepartment> sysDepartments = sysDepartmentService.list(null);

        return ResultVOUtil.success(sysDepartments);
    }

}

