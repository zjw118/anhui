package com.gistone.mapper;

import com.gistone.entity.CodeMenu;
import com.gistone.entity.Menu;

import java.util.List;

public interface MenuMapper {
    List<Menu> getMenuList();
    List<CodeMenu> getCodeMenuList();
}
