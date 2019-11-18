package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.St4ScsCt;
import com.gistone.mapper.St4ScsCtMapper;
import com.gistone.service.ISt4ScsCtService;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Service
public class St4ScsCtServiceImpl extends ServiceImpl<St4ScsCtMapper, St4ScsCt> implements ISt4ScsCtService {

    @Autowired
    private St4ScsCtMapper st4ScsCtMapper;

    @Override
    public Result insertSpaceData(St4ScsCt st4ScsCt) {
        Date date = new Date();
        st4ScsCt.setCt017(date);
        st4ScsCt.setCt019(date);
        st4ScsCt.setCt020(1);
        QueryWrapper<St4ScsCt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CT003", st4ScsCt.getCt003()).or().eq("CT004", st4ScsCt.getCt004());
        List<St4ScsCt> list = st4ScsCtMapper.selectList(queryWrapper);
        if (list.size() > 0) {
            AtomicReference<String> ret = new AtomicReference<>("");
            list.stream().forEach(st4ScsCt1 ->
                    ret.set(judgeCT003OrCT004(st4ScsCt, st4ScsCt1))
            );
            return Result.build(1008, ret.get() + ResultMsg.MSG_1008);
        }
        if (st4ScsCtMapper.insert(st4ScsCt) > 0) {
            return Result.build(1000, "添加" + ResultMsg.MSG_1000);
        }
        return Result.build(1003, ResultMsg.MSG_1003);
    }

    /**
     * @param st4ScsCt
     * @param st4ScsCt1
     * @return java.lang.String
     * @explain : 判断实体数据相同字段
     * @author xxh
     * @date 2019/8/12
     */
    public String judgeCT003OrCT004(St4ScsCt st4ScsCt, St4ScsCt st4ScsCt1) {
        if (st4ScsCt1.getCt003().equals(st4ScsCt.getCt003())) {
            return "数据名称";
        } else {
            return "数据URL";
        }
    }

    @Override
    public Result updateSpaceData(St4ScsCt st4ScsCt) {
        Date date = new Date();
        st4ScsCt.setCt019(date);
        st4ScsCt.setCt020(1);
        QueryWrapper<St4ScsCt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CT003", st4ScsCt.getCt003()).or().eq("CT004", st4ScsCt.getCt004());
        queryWrapper.eq("CT001", st4ScsCt.getCt001());
        List<St4ScsCt> list = st4ScsCtMapper.selectList(queryWrapper);
        if (list.size() > 1) {
            AtomicReference<String> ret = new AtomicReference<>("");
            list.stream().forEach(st4ScsCt1 ->
                    ret.set(judgeCT003OrCT004(st4ScsCt, st4ScsCt1))
            );
            return Result.build(1008, ret.get() + ResultMsg.MSG_1008);
        }
        if (st4ScsCtMapper.updateById(st4ScsCt) > 0) {
            return Result.build(1000, "修改" + ResultMsg.MSG_1000);
        }
        return Result.build(1003, ResultMsg.MSG_1003);
    }

    @Override
    public Result deleteSpaceData(St4ScsCt st4ScsCt) {
        st4ScsCt.setCt020(0);
        QueryWrapper<St4ScsCt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CT002", st4ScsCt.getCt001());
        int count = st4ScsCtMapper.selectCount(queryWrapper);//判断是否还有子集
        if (count > 0) {
            return Result.build(1006, "当前节点下还有子集，不能删除");

        }
        if (st4ScsCtMapper.updateById(st4ScsCt) > 0) {
            return Result.build(1000, "删除" + ResultMsg.MSG_1000);
        }
        return Result.build(1003, ResultMsg.MSG_1003);
    }
    @Override
    public Result listSpaceDataByPage(St4ScsCt st4ScsCt) {
        QueryWrapper<St4ScsCt> queryWrapper = queryWrapper(st4ScsCt);
        int total = st4ScsCtMapper.selectCount(queryWrapper);
        queryWrapper.last("limit " + st4ScsCt.getPageSize() * (st4ScsCt.getPageNumber()-1) + "," + st4ScsCt.getPageSize());
        queryWrapper.select("st4_scs_ct.CT001 AS ct001,st4_scs_ct.CT002 AS ct002,st4_scs_ct.CT003 AS ct003,st4_scs_ct.CT004 AS ct004,st4_scs_ct.CT005 AS ct005," +
                "st4_scs_ct.CT006 AS ct006,st4_scs_ct.CT007 AS ct007,st4_scs_ct.CT008 AS ct008,st4_scs_ct.CT009 AS ct009,st4_scs_ct.CT010 AS ct010,st4_scs_ct.CT011 AS ct011," +
                "st4_scs_ct.CT012 AS ct012,st4_scs_ct.CT013 AS ct013,st4_scs_ct.CT014 AS ct014,st4_scs_ct.CT015 AS ct015,st4_scs_ct.CT016 AS ct016,st4_scs_ct.CT017 AS ct017," +
                "st4_scs_ct.CT018 AS ct018,st4_scs_ct.CT019 AS ct019,st4_scs_ct.CT020 AS ct020,st4_scs_ct.SG001 AS sg001,st4_scs_ct.SD001 AS sd001," +
                //"IF(st4_scs_ct.SG001=0,'',(SELECT SG008 FROM st4_sys_sg  WHERE st4_sys_sg.SG001=st4_scs_ct.SG001)) AS sg008," +
                //"IF(st4_scs_ct.SD001=0,'',(SELECT SD008 FROM st4_sys_sd  WHERE st4_sys_sd.SD001=st4_scs_ct.SD001)) AS sd008," +
                "IF(st4_scs_ct.CT002=0,'',(SELECT CT003 FROM st4_scs_ct s1 WHERE s1.CT001=st4_scs_ct.CT002)) AS ct003Fname ");
        queryWrapper.orderByDesc("CT019");
        List<Map<String, Object>> list = st4ScsCtMapper.selectMaps(queryWrapper);
        int page = (st4ScsCt.getPageNumber() / st4ScsCt.getPageSize()) + 1;//当前页码
        Result result = new Result();
        result.setRows(list);
        result.setTotal(total);
        result.setPage(page);
        result.setStatus(1000);
        return result;
    }

    @Override
    public Result listSpaceDataTree(St4ScsCt st4ScsCt) {
        QueryWrapper<St4ScsCt> queryWrapper = queryWrapper(st4ScsCt);
        List<St4ScsCt> list = st4ScsCtMapper.selectList(queryWrapper);
        return Result.build(1000,"查询成功",buildTree(list, 0));
    }

    @Override
    public Result listSpaceDataType(St4ScsCt st4ScsCt) {
        st4ScsCt.setCt007(0);//默认查询数据分类的数据
        st4ScsCt.setCt002(0);//并且数据必须是父级节点的数据
        QueryWrapper<St4ScsCt> queryWrapper = queryWrapper(st4ScsCt);
        queryWrapper.select("CT001,CT003");
        List<Map<String, Object>> list = st4ScsCtMapper.selectMaps(queryWrapper);
        return Result.build(1000,"查询成功",list);
    }

    public static List<St4ScsCt> buildTree(List<St4ScsCt> list, Integer fid) {
        List<St4ScsCt> resultList = new ArrayList<St4ScsCt>();
        if (list == null || list.size() == 0 || fid < 0) {
            return null;
        }
        for (St4ScsCt tree : list) {
            if (tree.getCt002() == fid) {
                resultList.add(tree);
                tree.setChildren(buildTree(list, tree.getCt001()));
            }
        }
        return resultList;
    }


    /**
     * @param st4ScsCt
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.gistone.St4ScsCt>
     * @explain : 数据查询封装
     * @author xxh
     * @date 2019/8/12
     */
    public QueryWrapper<St4ScsCt> queryWrapper(St4ScsCt st4ScsCt) {
        QueryWrapper<St4ScsCt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CT020", 1);
        if (st4ScsCt.getCt002() != null || "".equals(st4ScsCt.getCt002())) {
            queryWrapper.like("CT002", st4ScsCt.getCt002());
        }
        if (st4ScsCt.getCt003() != null || "".equals(st4ScsCt.getCt003())) {
            queryWrapper.like("CT003", st4ScsCt.getCt003());
        }
        if (st4ScsCt.getCt004() != null || "".equals(st4ScsCt.getCt004())) {
            queryWrapper.like("CT004", st4ScsCt.getCt004());
        }
        if (st4ScsCt.getCt005() != null || "".equals(st4ScsCt.getCt005())) {
            queryWrapper.like("CT005", st4ScsCt.getCt005());
        }
        if (st4ScsCt.getCt006() != null || "".equals(st4ScsCt.getCt006())) {
            queryWrapper.eq("CT006", st4ScsCt.getCt006());
        }
        if (st4ScsCt.getCt007() != null || "".equals(st4ScsCt.getCt007())) {
            queryWrapper.eq("CT007", st4ScsCt.getCt007());
        }
        if (st4ScsCt.getCt008() != null || "".equals(st4ScsCt.getCt008())) {
            queryWrapper.like("CT008", st4ScsCt.getCt008());
        }
        if (st4ScsCt.getCt009() != null || "".equals(st4ScsCt.getCt009())) {
            queryWrapper.like("CT009", st4ScsCt.getCt009());
        }
        if (st4ScsCt.getCt010() != null || "".equals(st4ScsCt.getCt010())) {
            queryWrapper.like("CT010", st4ScsCt.getCt010());
        }
        if (st4ScsCt.getCt011() != null || "".equals(st4ScsCt.getCt011())) {
            queryWrapper.like("CT011", st4ScsCt.getCt011());
        }
        if (st4ScsCt.getCt013() != null || "".equals(st4ScsCt.getCt013())) {
            queryWrapper.like("CT013", st4ScsCt.getCt013());
        }
        if (st4ScsCt.getCt014() != null || "".equals(st4ScsCt.getCt014())) {
            queryWrapper.eq("CT014", st4ScsCt.getCt014());
        }
        if (st4ScsCt.getCt015() != null || "".equals(st4ScsCt.getCt015())) {
            queryWrapper.eq("CT015", st4ScsCt.getCt015());
        }
        if (st4ScsCt.getCt016() != null || "".equals(st4ScsCt.getCt016())) {
            queryWrapper.eq("CT016", st4ScsCt.getCt016());
        }
        if (st4ScsCt.getSg001() != null || "".equals(st4ScsCt.getSg001())) {
            queryWrapper.eq("SG001", st4ScsCt.getSg001());
        }
        if (st4ScsCt.getSd001() != null || "".equals(st4ScsCt.getSd001())) {
            queryWrapper.eq("SD001", st4ScsCt.getSd001());
        }
        return queryWrapper;
    }
}
