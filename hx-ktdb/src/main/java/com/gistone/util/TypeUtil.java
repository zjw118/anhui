package com.gistone.util;

import com.gistone.entity.BottomchartType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zf1017@foxmail.com
 * @date 2019/11/13 0013 17:01
 * @description
 */
public class TypeUtil {
    public static List<BottomchartType> buildTree(List<BottomchartType> list, int parentId){
        List<BottomchartType> menus=new ArrayList<BottomchartType>();
        for (BottomchartType menu : list) {

            int menuId = menu.getId();
            int pid = menu.getFId();
            if (parentId == pid) {
                List<BottomchartType> menuLists = buildTree(list, menuId);

                menu.setChildren(menuLists);
                menus.add(menu);
            }
        }

        return menus;
    }
}
