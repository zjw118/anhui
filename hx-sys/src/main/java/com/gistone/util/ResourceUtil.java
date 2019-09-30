package com.gistone.util;

import com.gistone.entity.Menu;
import com.gistone.entity.SysResources;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/6 0006 10:42
 * @description
 */
public class ResourceUtil {
    public static Set<SysResources> buildTree(List<SysResources> list, int parentId) {
        Set<SysResources> menus = new HashSet<>();
        for (SysResources sysResources : list) {
            sysResources.setTitle(sysResources.getName());
            int sysResourcesId = sysResources.getId();
            int pid = sysResources.getParentId();
            if (parentId == pid) {
                Set<SysResources> sysResourcesList = buildTree(list, sysResourcesId);
                Set<SysResources> newList =  sysResourcesList.stream().sorted(Comparator.comparing(SysResources::getSort))
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                sysResources.setChildren(newList);
                menus.add(sysResources);
            }

        }
        return menus;
    }

    public static Set<SysResources> buildTree(Set<SysResources> list, int parentId) {
        Set<SysResources> menus = new HashSet<>();
        for (SysResources sysResources : list) {
            sysResources.setTitle(sysResources.getName());
            int sysResourcesId = sysResources.getId();
            int pid = sysResources.getParentId();
            if (parentId == pid) {
                Set<SysResources> sysResourcesList = buildTree(list, sysResourcesId);
                sysResources.setChildren(sysResourcesList);
                menus.add(sysResources);
            }
        }
        return menus;
    }

}
