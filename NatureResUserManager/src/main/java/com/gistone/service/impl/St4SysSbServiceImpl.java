package com.gistone.service.impl;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.entity.*;
import com.gistone.mapper.St4PoSbScMapper;
import com.gistone.mapper.St4SysSbMapper;
import com.gistone.mapper.St4SysScMapper;
import com.gistone.mapper.St4SysShMapper;
import com.gistone.service.ISt4PoSbScService;
import com.gistone.service.ISt4SysSbService;
import com.gistone.util.Result;
import com.gistone.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjw
 * @since 2019-08-28
 */
@Service
public class St4SysSbServiceImpl extends ServiceImpl<St4SysSbMapper, St4SysSb> implements ISt4SysSbService {
    @Autowired
    private St4SysSbMapper st4SysSbMapper;
    @Autowired
    private St4SysScMapper st4SysScMapper;
    @Autowired
    private St4PoSbScMapper st4PoSbScMapper;

    @Autowired
    private St4SysShMapper st4SysShMapper;

    @Autowired
    private ISt4PoSbScService st4PoSbScService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Result listRole(St4SysSb sb, HttpServletRequest request){

        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        QueryWrapper<St4SysSb> wrapper = new QueryWrapper<>();
        wrapper.eq("SB003",1);
        wrapper.eq("SB005",userId);
        if(sb.getSb002()!=null){
            wrapper.like("SB002",sb.getSb002());
        }
        Page<St4SysSb> page = new Page<>(sb.getPageNumber(),sb.getPageSize());
        IPage<St4SysSb> iPage = st4SysSbMapper.selectPage(page,wrapper);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setRows(iPage.getRecords());
        result.setTotal((int)iPage.getTotal());
        result.setPage((int)iPage.getPages());
        return result;
    }
    @Override
    public Result listRoleNoLimit(St4SysSb sb, HttpServletRequest request){
        //这里涉及到的权限的话
        //就应该是比如说天津管理员或者其他省的管理员是看不到除自己以外的管理员的角色的

        QueryWrapper<St4SysSb> wrapper = new QueryWrapper<>();
        wrapper.eq("SB003",1);
        wrapper.eq("SB009",0);
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        String userId = JWT.decode(token).getAudience().get(0);
        //这里加这个限制条件就是让山西管理员进来之后只能看见自己创建的角色
        wrapper.eq("SB005",userId);
        //这里还要注意天津的管理员只能看到自己创建的角色 是看不到其他省的管理员创建的角色的
        List<St4SysSb> list = st4SysSbMapper.selectList(wrapper);
        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setData(list);
        return result;

    }

    @Override
    public Result insertRole(St4SysSb sb, St4SysSa seUser) {
       List<Integer> prileledgeIds = sb.getPriviledgeIds();
       St4PoSbSc sbsc = null;
       List<St4PoSbSc> sbscList = new ArrayList<>();
       Integer roleNum = st4SysSbMapper.insert(sb);
       //日志
        St4SysSh sh = new St4SysSh();
        sh.setSh002(seUser.getSa001());
        LocalDateTime date = LocalDateTime.now();
        sh.setSh003(date);
        sh.setSh014("新增角色");
        st4SysShMapper.insert(sh);
       Integer roleId = sb.getSb001();
       if(prileledgeIds!=null&&prileledgeIds.size()>0){
           for (Integer priId:prileledgeIds) {
               sbsc = new St4PoSbSc();
               sbsc.setSc001(priId);
               sbsc.setSb001(roleId);
               sbscList.add(sbsc);
           }
           boolean flag = st4PoSbScService.saveBatch(sbscList);
           if(!flag){
               return  Result.build(1003, ResultMsg.MSG_1003);
           }
       }

        if(roleNum>0){
            return Result.build(1000,"添加"+ ResultMsg.MSG_1000);
        }else{
            return Result.build(1003,ResultMsg.MSG_1003);
        }

    }

    @Override
    public Result updateRole(St4SysSb sb,St4SysSa seUser) {
        //先把这个角色原来的权限信息删除
        QueryWrapper<St4PoSbSc> sbScQueryWrapper = new QueryWrapper<>();
        sbScQueryWrapper.eq("SB001",sb.getSb001());
        int delNum = st4PoSbScMapper.delete(sbScQueryWrapper);

        List<Integer> prileledgeIds = sb.getPriviledgeIds();
        St4PoSbSc sbsc = null;
        List<St4PoSbSc> sbscList = new ArrayList<>();
        sb.setSb007(seUser.getSa001());
        Integer roleNum = st4SysSbMapper.updateById(sb);
        //日志
        St4SysSh sh = new St4SysSh();
        sh.setSh002(seUser.getSa001());
        LocalDateTime date = LocalDateTime.now();
        sh.setSh003(date);
        sh.setSh014("修改角色");
        st4SysShMapper.insert(sh);
        Integer roleId = sb.getSb001();
        if(prileledgeIds!=null&&prileledgeIds.size()>0){
            for (Integer priId:prileledgeIds) {
                sbsc = new St4PoSbSc();
                sbsc.setSc001(priId);
                sbsc.setSb001(roleId);
                sbscList.add(sbsc);
            }
            boolean flag = st4PoSbScService.saveBatch(sbscList);
            if(!flag){
                return  Result.build(1003,ResultMsg.MSG_1003);
            }
        }
        if(roleNum>0){
            return Result.build(1000,"修改"+ ResultMsg.MSG_1000);
        }else{
            return Result.build(1003,ResultMsg.MSG_1003);
        }

    }

    @Override
    public Result getRoleDetail(St4SysSb sb) {
        sb=st4SysSbMapper.getRoleDetail(sb);
        //此处要回显所属角色的权限集合

        QueryWrapper<St4PoSbSc> wrapper = new QueryWrapper<>();
        wrapper.eq("SB001",sb.getSb001());
        List<St4PoSbSc> sbscList = st4PoSbScMapper.selectList(wrapper);
        List<Integer> scIds = new ArrayList<>();
        if(sbscList!=null&&sbscList.size()>0){
            for (St4PoSbSc sbsc:sbscList) {
                scIds.add(sbsc.getSc001());
            }
            QueryWrapper<St4SysSc> wrapperSc = new QueryWrapper<>();
            wrapperSc.in("SC001",scIds);
            List<St4SysSc> list = st4SysScMapper.selectList(wrapperSc);
            if(list!=null&&list.size()>0){
                sb.setSt4SysScList(list);
            }
        }

        Result result = new Result();
        result.setStatus(1000);
        result.setData(sb);
        result.setMsg("加载成功");

        return result;
    }
}
