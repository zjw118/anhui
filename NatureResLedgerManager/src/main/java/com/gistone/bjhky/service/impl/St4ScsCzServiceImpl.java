package com.gistone.bjhky.service.impl;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.entity.St4PoSaCz;
import com.gistone.bjhky.entity.St4ScsCz;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.mapper.St4PoSaCzMapper;
import com.gistone.bjhky.mapper.St4ScsCzMapper;
import com.gistone.bjhky.mapper.St4SysSaMapper;
import com.gistone.bjhky.service.ISt4PoSaCzService;
import com.gistone.bjhky.service.ISt4ScsCzService;
import com.gistone.bjhky.swagger.St4ScsCzSwagger;
import com.gistone.bjhky.util.Result;
import com.gistone.bjhky.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 问题点分组表 服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-22
 */
@Service
public class St4ScsCzServiceImpl extends ServiceImpl<St4ScsCzMapper, St4ScsCz> implements ISt4ScsCzService {

    @Autowired
    private  St4ScsCzMapper st4ScsCzMapper;
    @Autowired
    private  St4PoSaCzMapper st4PoSaCzMapper;
    @Autowired
    private  St4SysSaMapper st4SysSaMapper;
    @Autowired
    private ISt4PoSaCzService iSt4PoSaCzService;
    @Override
    public Result listGroup(St4ScsCz cz,St4SysSa seUser) {
        int size = cz.getPageSize();//每页条数
        int number = cz.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        cz.setPageNumber(numberReal);
        cz.setPageSize(size);
        seUser = st4SysSaMapper.selectById(seUser);
        cz.setCz004(seUser.getSa001());
        if(seUser.getSa001()==1){
            cz.setType(2);
        }else{
            if(seUser.getSa020()==0){
                cz.setType(0);
            }else  {
                cz.setType(1);
            }
        }
        List<St4ScsCz> list = st4ScsCzMapper.listGroup(cz);
        List<Integer> czList=new ArrayList<>();
        Integer j=0;
        if(list!=null&&list.size()>0){
            for (St4ScsCz czz:list) {
                czList.add(czz.getCz001());
                if(czz.getCz006()!=null){
                    list.get(j).setCaptainName(st4SysSaMapper.selectById(czz.getCz006()).getSa019());
                }
                j++;
            }
        }
        Integer i=0;

        List<St4SysSa> saList = null;
        if(czList!=null&&czList.size()>0){
            for (Integer czn:czList) {
                QueryWrapper<St4PoSaCz> wapper = new QueryWrapper<>();
                wapper.eq("CZ001",czn);
                List<St4PoSaCz> saczList = st4PoSaCzMapper.selectList(wapper);
                if(saczList.size()>0){
                    saList = new ArrayList<>();
                    for (St4PoSaCz sacz:saczList) {
                        saList.add(st4SysSaMapper.selectById(sacz.getSa001()));
                    }
                    list.get(i).setSt4SysSaList(saList);
                    i++;
                }
            }
        }



        cz.setPageNumber(null);
        cz.setPageSize(null);
        Result result = new Result();
        result.setStatus(1000);
        Integer tsize =st4ScsCzMapper.listGroup(cz).size();
        result.setTotal(tsize);

        result.setPage((int)Math.ceil((double)tsize/size));
        result.setMsg("加载"+ ResultMsg.MSG_1000);
        result.setRows(list);
        return result;
    }

    @Override
    public Result getGroupUserDetail(St4ScsCz cz) {
        Result result = new Result();

        List<St4SysSa> list = st4ScsCzMapper.getGroupUserDetail(cz);
        result.setStatus(1000);
        QueryWrapper<St4ScsCz> wrapper = new QueryWrapper<>();
        wrapper.eq("CZ001",cz.getCz001());
        List<St4ScsCz> czList = st4ScsCzMapper.selectList(wrapper);
        czList.get(0).setSt4SysSaList(list);
        Integer captainId = czList.get(0).getCz006();
        St4SysSa sa = st4SysSaMapper.selectById(captainId);
        if(captainId!=0){
            czList.get(0).setCaptainName(sa.getSa008());
            czList.get(0).setCaptainRealName(sa.getSa019());
            czList.get(0).setCaptainPhone(sa.getSa012());
        }
        result.setMsg("加载"+ ResultMsg.MSG_1000);
        result.setData(czList);

        return result;
    }

    @Override
    public Result updateGroupData(St4ScsCzSwagger sw) {
        Result result = new Result();

        St4ScsCz cz = st4ScsCzMapper.selectById(sw.groupId);
        //先把小组名称和组长名称改变
        if(sw.groupName!=null){
            cz.setCz002(sw.groupName);
        }

        if(sw.captainId!=null){
            cz.setCz006(sw.captainId);
        }
        if(sw.taskId!=null){
            cz.setCl001(sw.taskId);
        }
        //将原来的组长信息设置为最新的
        int num1 = st4ScsCzMapper.updateById(cz);

        if(num1<1){
            return Result.build(1003,ResultMsg.MSG_1003);
        }



        //这里如果是之前有的小组就先将原来的分组绑定的信息删除
        St4PoSaCz czz = new St4PoSaCz();
        QueryWrapper<St4PoSaCz> saCzQueryWrapper = new QueryWrapper<>();
        saCzQueryWrapper.eq("CZ001",sw.groupId);
        if(st4PoSaCzMapper.selectList(saCzQueryWrapper).size()>0){
            QueryWrapper<St4PoSaCz> wrapper = new QueryWrapper<>();
            wrapper.eq("CZ001",cz.getCz001());
            if(st4PoSaCzMapper.delete(wrapper)<1){
                return  Result.build(1003,ResultMsg.MSG_1003);
            };
        }


        czz = null;

        List<St4PoSaCz> saczList = new ArrayList<>();

        List<Integer> uidList = sw.uidList;

        Set<Integer> uidSet = new HashSet<>();
        for (Integer uid:uidList) {
            uidSet.add(uid);
        }
        for (Integer uid:uidSet) {
            czz = new St4PoSaCz();
            czz.setSa001(uid);
            czz.setCz001(cz.getCz001());
            saczList.add(czz);
        }


        if(iSt4PoSaCzService.saveBatch(saczList)){
            return Result.build(1000,"分配"+ResultMsg.MSG_1000);
        }


        return Result.build(1005,ResultMsg.MSG_1005);
    }

    @Override
    public Result insertGroupData(St4ScsCzSwagger swagger, HttpServletRequest request) {
        Result result = new Result();
        List<Integer> uidsList = swagger.uidList;
        //这里得保证同一个任务下 一个人不能同时在多个小组
        /**
         * 1.拿着这个任务的id去查小组，把每个小组下的人的id都放到list里面
         * 2.list和传递过来的uidList作比较只要有重合的
         */

        St4ScsCz cz = new St4ScsCz();
        //任务ID在这里是必传的，但是现在还未传递
        if(swagger.taskId!=null){
            cz.setCl001(swagger.taskId);
        }
        cz.setCz002(swagger.groupName);
        if(swagger.captainId!=null){
            cz.setCz006(swagger.captainId);
        }
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        cz.setCz004(Integer.valueOf(userId));
        LocalDateTime date = LocalDateTime.now();
        cz.setCz005(date);
        Integer czId=0;
        if(st4ScsCzMapper.insert(cz)>0){
            czId =cz.getCz001();
        };
        List<St4PoSaCz> saczList = new ArrayList<>();

        St4PoSaCz czz = null;
        List<Integer> uidList = swagger.uidList;
        Set<Integer> uidSet = new HashSet<>();
        for (Integer uid:uidList) {
            uidSet.add(uid);
        }
        for (Integer uid:uidSet) {
            czz = new St4PoSaCz();
            czz.setSa001(uid);
            czz.setCz001(czId);
            saczList.add(czz);
        }

        if(iSt4PoSaCzService.saveBatch(saczList)){
            return Result.build(1000,"添加"+ResultMsg.MSG_1000);
        }
        return result;
    }

    @Override
    public Result showUserInCheck() {
        List<St4SysSa> list = st4SysSaMapper.showUserInCheck();

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(list);

        return result;
    }
}
