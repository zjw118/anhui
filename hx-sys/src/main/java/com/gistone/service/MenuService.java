package com.gistone.service;

import com.gistone.entity.CodeMenu;
import com.gistone.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuList();
    List<CodeMenu> getCodeMenuList();
}
