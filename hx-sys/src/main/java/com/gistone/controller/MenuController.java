package com.gistone.controller;

import com.gistone.VO.ResultVO;
import com.gistone.entity.CodeMenu;
import com.gistone.entity.Menu;
import com.gistone.service.MenuService;
import com.gistone.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sys/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/getMenu")
    public ResultVO getMenu(){

        List<Menu> menuList = menuService.getMenuList();

        return ResultVOUtil.success(menuList);
    }


    @RequestMapping("/getCodeMenu")
    public ResultVO getCodeMenu(){
        List<CodeMenu> codeMenuList = menuService.getCodeMenuList();
        return ResultVOUtil.success(codeMenuList);
    }
}
