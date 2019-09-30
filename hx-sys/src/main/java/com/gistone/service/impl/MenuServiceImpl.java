package com.gistone.service.impl;

import com.gistone.entity.CodeMenu;
import com.gistone.entity.Menu;
import com.gistone.mapper.MenuMapper;
import com.gistone.service.MenuService;
import com.gistone.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuList() {
        List<Menu> menuList = menuMapper.getMenuList();
        List<Menu> result = MenuUtil.buildTree(menuList, 0);
        return result;
    }

    @Override
    public List<CodeMenu> getCodeMenuList() {
        List<CodeMenu> codeMenuList = menuMapper.getCodeMenuList();
        List<CodeMenu> result = MenuUtil.buildTree2(codeMenuList,0);

        return result;
    }
}
