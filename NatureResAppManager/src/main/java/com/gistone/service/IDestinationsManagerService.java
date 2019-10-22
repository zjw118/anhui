package com.gistone.service;

import com.gistone.entity.*;

import java.util.List;

/**
 * @ClassName IDestinationsManager
 * @Description TODO
 * @Author xxh
 * @Date 2019/8/15 9:05
 * @Version 1.0
 **/
public interface IDestinationsManagerService {

    /**
     * @explain : 存储航点调查信息
     * @author xxh
     * @date 2019/8/15
     * @param ccList 航点基础信息
     * @param ckList 台账信息
     * @param cfList 巡护信息
     * @param ceList 附件信息
     * @return Result
     */
    Result insertDestinationsManager(List<St4ScsCc> ccList, List<St4ScsCk> ckList, List<St4ScsCf> cfList, List<St4ScsCe> ceList);

    /**
     * @explain :
     * @author xxh
     * @date 2019/8/16
     * @param scsCy 巡护记录
     * @param st4ScsCgs 巡护路段记录集合
     * @param ccList 航点基础信息
     * @param ckList 台账信息
     * @param cfList 巡护信息
     * @param ceList 附件信息
     * @return Result
     */
    Result insertDestinationsRecordManager(St4ScsCy scsCy, List<St4ScsCg> st4ScsCgs, List<St4ScsCc> ccList, List<St4ScsCk> ckList, List<St4ScsCf> cfList, List<St4ScsCe> ceList);



}
