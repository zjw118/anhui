package com.gistone.util;

import com.gistone.entity.CodeMenu;
import com.gistone.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {

    public static List<Menu> buildTree(List<Menu> list, int parentId){
        List<Menu> menus=new ArrayList<Menu>();
        for (Menu menu : list) {

            int menuId = menu.getValue();
            int pid = menu.getComFpkid();
            if (parentId == pid) {
                List<Menu> menuLists = buildTree(list, menuId);

                menu.setChildren(menuLists);
                menus.add(menu);
            }
        }

        return menus;
    }

    public static List<CodeMenu> buildTree2(List<CodeMenu> list, int parentId){
        List<CodeMenu> menus=new ArrayList<CodeMenu>();
        for (CodeMenu menu : list) {

            int menuId = menu.getId();
            int pid = menu.getComFpkid();
            if(parentId<=2) {
                if (parentId == pid) {
                    List<CodeMenu> menuLists = buildTree2(list, menuId);

                    menu.setChildren(menuLists);
                    menus.add(menu);
                }
            }
        }

        return menus;
    }
}
