package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gistone.entity.St4PoCdSa;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.SysUser;
import com.gistone.mapper.St4PoCdSaMapper;
import com.gistone.mapper.St4SysSaMapper;
import com.gistone.mapper.SysUserMapper;
import com.gistone.service.ISt4PoCdSaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.util.JPushUtil;
import com.gistone.util.Result;
import com.gistone.util.ResultCp;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 核查点分配记录表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-15
 */
@Service
public class St4PoCdSaServiceImpl extends ServiceImpl<St4PoCdSaMapper, St4PoCdSa> implements ISt4PoCdSaService {
    @Autowired
    private St4PoCdSaMapper st4PoCdSaMapper;

    @Autowired
    private ISt4PoCdSaService st4PoCdSaService;

    @Autowired
    private SysUserMapper st4SysSaMapper;


    @Override
    public ResultCp givePoint(List<Integer> uids, List<Integer> pointList) {
        List<St4PoCdSa> existsaList = null;
        St4PoCdSa saSg=null;
        boolean flag=true;
        //准备判断这个点是否被重新复下发给同一个人
        for (Integer rid:pointList) {
            List<St4PoCdSa> list = new ArrayList<>();
            existsaList = new ArrayList<>();
            QueryWrapper<St4PoCdSa> sasgWrapper = new QueryWrapper<>();  //关联表
            sasgWrapper.eq("CD001",rid);
            existsaList = st4PoCdSaMapper.selectList(sasgWrapper);
            List<Integer> ridExist=null;
            if(existsaList!=null&&existsaList.size()>0){
                ridExist = new ArrayList<>();

                if(existsaList.size()>0){
                    for (St4PoCdSa sasg:existsaList) {
                        ridExist.add(sasg.getSa001());
                    }
                }
                if(ridExist!=null&&ridExist.size()>0){
                    for (Integer reid:uids) {
                        if(!ridExist.contains(reid)){
                            ridExist.add(reid);
                        }
                    }
                }
                if(st4PoCdSaMapper.delete(sasgWrapper)>0){
                    if(ridExist!=null&&ridExist.size()>0){
                        for (Integer rids:ridExist) {
                            saSg = new St4PoCdSa();
                            saSg.setSa001(rids);
                            saSg.setCd001(rid);
                            list.add(saSg);
                        }
                    }
                    if(!st4PoCdSaService.saveBatch(list)){
                        flag=false;
                        break;
                    }
                }
            }else {
                if(uids!=null&&uids.size()>0){
                    for (Integer uid:uids) {
                        saSg = new St4PoCdSa();
                        saSg.setSa001(uid);
                        saSg.setCd001(rid);
                        list.add(saSg);
                    }
                }
                if(!st4PoCdSaService.saveBatch(list)){
                    flag=false;
                    break;
                }
            }

        }



        if(flag){
                QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
                wrapper.in("id",uids);
                List<SysUser> saList = st4SysSaMapper.selectList(wrapper);
                /*for (SysUser saa:saList) {
                    JPushUtil.jiGuangPush(saa.getSa012(), "您有新的问题点需要接收！","1");
                }*/

            return ResultCp.build(1000,"问题点分配"+ResultMsg.MSG_1000);
        }
        return ResultCp.build(1005,ResultMsg.MSG_1005);
    }
}
