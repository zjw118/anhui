package com.gistone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.St4PoSaSj;
import com.gistone.entity.St4SysSa;
import com.gistone.entity.St4SysSj;
import com.gistone.mapper.St4PoSaSjMapper;
import com.gistone.mapper.St4SysSaMapper;
import com.gistone.mapper.St4SysSjMapper;
import com.gistone.service.ISt4PoSaSjService;
import com.gistone.service.ISt4SysSaService;
import com.gistone.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author xxh
 * @since 2019-08-12
 */
@Service
public class St4SysSaServiceImpl extends ServiceImpl<St4SysSaMapper, St4SysSa> implements ISt4SysSaService {

    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private St4SysSjMapper st4SysSjMapper;
    @Autowired
    private St4PoSaSjMapper st4PoSaSjMapper;
    @Autowired
    private ISt4SysSaService st4SysSaService;
    @Autowired
    private ISt4PoSaSjService st4PoSaSjService;



    @Override
    public ResultCp searchSysUserByLogin(String username, String password){
       /* QueryWrapper<St4SysSa> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SA008", username).or().eq("SA012",username);
        queryWrapper.eq("SA007",1);*/
        St4SysSa da = new St4SysSa();
        da.setSa008(username);
        da.setSa012(username);
        da.setSa007(1);
        List<St4SysSa> list = st4SysSaMapper.login(da);
        if (list.size() > 0 && list.size() < 2) {
            St4SysSa sysUser = list.stream().filter(sysUser1 -> sysUser1.getSa009().equals(Md5Util.md5Encode(password))).findAny().orElse(null);
            if (sysUser != null) {
                return ResultCp.build(1000, "登录成功",sysUser);
            }
            return ResultCp.build(1017, "登录失败，密码错误");
        }
        return ResultCp.build(1006, list.size() > 1 ? "登录失败，平台内账号重复，请联系管理员" : "登录失败，用户不存在");
    }

    @Override
    public ResultVO listPhoneUserToView(St4SysSa data) {
        List<Map> list = st4SysSaMapper.listPhoneUserToView(data);
        Result result = new Result();
        result.setData(list);
        return ResultVOUtil.success(result);
    }
    @Override
    public  Result list(St4SysSa sa,St4SysSa seUser){
        Result result = new Result();
        QueryWrapper<St4SysSa> wrapper = new QueryWrapper<St4SysSa>();
        seUser = st4SysSaMapper.selectById(seUser);
        sa.setSa001(seUser.getSa001());
        sa.setSa002(seUser.getSa002());
        if(seUser.getSa001()==1){
            sa.setPtype(2);
        }else if(seUser.getSa020()==0){
            sa.setPtype(0);
        }else if(seUser.getSa020()==1){
            sa.setPtype(1);
        }
       List<St4SysSa> list = st4SysSaMapper.listNolimit(sa);
       result.setData(list);
        result.setStatus(1000);
        result.setMsg("加载成功");
        return result;
    }

    @Override
    public ResultVO listUser(St4SysSa sa,St4SysSa seUser) {
       /* Result result = new Result();
        PageBean pageBean = new PageBean();
        pageBean.setStr1(sa.getSa008()); //条件

        pageBean.setPageIndex(Integer.valueOf(sa.getPageNumber()+""));
        pageBean.setPageSize(Integer.valueOf(sa.getPageSize()+""));
        pageBean.setPoSum(sysUserMapper.getPoSum(pageBean));
        pageBean.setPoList(sysUserMapper.selectPoList(pageBean));
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setRows(sysUserMapper.selectPoList(pageBean));
        result.setPage(pageBean.getPageSum());
        result.setTotal(pageBean.getPoSum());
        return result;*/
        Page<St4SysSa> page = new Page<>(sa.getPageNumber(),sa.getPageSize());
        Result result = new Result();
        QueryWrapper<St4SysSa> wrapper = new QueryWrapper<St4SysSa>();
        seUser = st4SysSaMapper.selectById(seUser);
        seUser.setSa001(seUser.getSa001());
        seUser.setSa002(seUser.getSa002());
        if(seUser.getSa001()==1){
            seUser.setPtype(2);
        }else if(seUser.getSa020()==0){
            seUser.setPtype(0);
        }else if(seUser.getSa020()==1){
            seUser.setPtype(1);
        }

        IPage<St4SysSa> iPage = st4SysSaMapper.listUser(page,sa);

        result.setRows(iPage.getRecords());
        result.setPage((int)iPage.getPages());
        result.setTotal((int)iPage.getTotal());

        return ResultVOUtil.success(result);
    }

    @Override
    public ResultCp updateAppUser(St4SysSa data) throws Exception {
        ResultCp result = new ResultCp();
        //如果旧密码不为空，则根据用户名验证密码是否正确
        if (RegUtil.CheckParameter(data.getSa009Old(), "String", null, false)) {
            String password = RSAEncrypt.decrypt(data.getSa009Old(), ResultMsg.KEY.toString());//密码解密
            St4SysSa selectData = st4SysSaMapper.selectById(data.getSa001());
            password = Md5Util.md5Encode(password);
            if(selectData != null && selectData.getSa009() !=null){
                if(!password.equals(selectData.getSa009())){
                    result.setMsg("原密码错误");
                    result.setStatus(1006);
                    return result;
                }
            }
        }
        //如果密码不为空，则为修改密码操作
        if (RegUtil.CheckParameter(data.getSa009(), "String", null, false)) {
            String password = RSAEncrypt.decrypt(data.getSa009(), ResultMsg.KEY.toString());//密码解密
            data.setSa009(Md5Util.md5Encode(password));
        }

        int row = st4SysSaMapper.updateById(data);

        if(row > 0){
            result.setMsg("修改成功");
            result.setStatus(1000);
        }else{
            result.setMsg("修改失败");
            result.setStatus(1006);
        }
        return result;
    }
    @Override
    public ResultVO getUserDetail(St4SysSa sa){
        sa = st4SysSaMapper.selectById(sa);
        sa.setSa009("");//此处密码不能返回

        QueryWrapper<St4PoSaSj> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("SA001",sa.getSa001());
        List<St4PoSaSj> sasjList = st4PoSaSjMapper.selectList(wrapper1);
        List<Integer> unitList = new ArrayList<>();
        if(sasjList.size()>0){
            for (St4PoSaSj saSj :sasjList) {
                unitList.add(saSj.getSj001());
            }
            QueryWrapper<St4SysSj> sjWrapper = new QueryWrapper<>();
            sjWrapper.in("SJ001",unitList);
            List<St4SysSj> sjList = st4SysSjMapper.selectList(sjWrapper);
            sa.setSjList(sjList);
        }
        return ResultVOUtil.success(sa);
    }
    //修改用户
    @Override
    public  ResultVO updateUser(St4SysSa user,St4SysSa seUser,List<Integer> roleList, List<Integer> unitList)  {

        //1.数据处理
        if(user.getSa009()!=null&&!"".equals(user.getSa009().trim())){
            //MD5加密
            user.setSa009(Md5Util.md5Encode(user.getSa009()));
        }else{
            St4SysSa user1  = st4SysSaMapper.selectById(user);
            user.setSa009(user1.getSa009());
        }
        user.setSa004(seUser.getSa004());
        LocalDateTime date = LocalDateTime.now();
        user.setSa005(date);

        //2.调用Dao
        int number = st4SysSaMapper.updateById(user);
        Integer uid = user.getSa001();
        if(number>0){
            /**
             * 这里修改完基本信息的之后角色也要重新设置
             */
           /* if(roleList!=null&&roleList.size()>0){
                QueryWrapper<St4PoSaSb> wrapper = new QueryWrapper<St4PoSaSb>();
                wrapper.eq("SA001",user.getSa001());
                //如果当前人原来有绑定的角色那么就删除原来的
                if(st4PoSaSbMapper.selectList(wrapper).size()>0){
                    st4PoSaSbMapper.delete(wrapper);
                }
                St4PoSaSb surr = null;
                List<St4PoSaSb> surrList = new ArrayList<>();

                for (Integer roleId:roleList ) {
                    surr = new St4PoSaSb();
                    surr.setSa001(uid);
                    surr.setSb001(roleId);
                    surrList.add(surr);
                }
                boolean rFlag = st4PoSaSbService.saveBatch(surrList);
                if(!rFlag){
                    return Result.build(1003,ResultMsg.MSG_1003);
                }
            }*/
            if(unitList!=null&&unitList.size()>0){
                QueryWrapper<St4PoSaSj> wrapper = new QueryWrapper<St4PoSaSj>();
                wrapper.eq("SA001",user.getSa001());
                //如果当前人原来有绑定的角色那么就删除原来的
                if(st4PoSaSjMapper.selectList(wrapper).size()>0){
                    st4PoSaSjMapper.delete(wrapper);
                }
                St4PoSaSj surr = null;
                List<St4PoSaSj> surrList = new ArrayList<>();

                for (Integer unitId:unitList ) {
                    surr = new St4PoSaSj();
                    surr.setSa001(uid);
                    surr.setSj001(unitId);
                    surrList.add(surr);
                }
                boolean uFlag = st4PoSaSjService.saveBatch(surrList);
                if(!uFlag){
                    return ResultVOUtil.error("1222","处理结果失败");
                }
            }
            //重新给当前人赋予新的角色
            if(number>0){
                return ResultVOUtil.success();
            };
        }else{
            return ResultVOUtil.error("1222","处理结果失败");
        }
        return ResultVOUtil.error("1222","处理结果失败");

    }

//    //添加用户
    @Override
    public ResultVO add(St4SysSa user,St4SysSa seUser,List<Integer> roleList,List<Integer> unitList) {

        //1.判断用户是否存在
        QueryWrapper<St4SysSa> wrapper = new QueryWrapper<>();
        wrapper.eq("SA008",user.getSa008());
        List<St4SysSa> userList = st4SysSaMapper.selectList(wrapper);
        if (userList.size() > 0) {
            return ResultVOUtil.error(ResultEnum.PARAMETEREMPTY.getCode(), "用户名重复！");

        }else{
            //2.添加用户
            //时间处理

            user.setSa009(Md5Util.md5Encode(user.getSa009()));
            LocalDateTime date = LocalDateTime.now();
            user.setSa003(date);	//添加时间
            user.setSa007(1);
            user.setSa002(seUser.getSa001());
            int flag =st4SysSaMapper.insert(user);
                    //st4SysSaService.save(user);
            int userId = user.getSa001();

           // List<St4PoSaSb> surrList = new ArrayList<>();

            if(userId>0){
                /**
                 * 给用户角色关联表中插入记录
                 */

               /* St4PoSaSb surr = null;
                if(roleList!=null&&roleList.size()>0){

                    for (Integer roleId :  roleList) {
                        surr = new St4PoSaSb();
                        surr.setSb001(roleId);
                        surr.setSa001(userId);
                        surrList.add(surr);
                    }
                    boolean batchRoleFlag = st4PoSaSbService.saveBatch(surrList);
                    if(!batchRoleFlag){
                        return Result.build(1003,ResultMsg.MSG_1003);
                    }
                }*/
                /**
                 *给用户单位表中插入记录
                 */
				List<St4PoSaSj> suurList = new ArrayList<>();
				St4PoSaSj suur = null;
				if(unitList!=null&&unitList.size()>0){

                    for (Integer unitId :  unitList) {
                        suur = new St4PoSaSj();
                        suur.setSa001(userId);
                        suur.setSj001(unitId);
                        suurList.add(suur);
                    }
                    boolean batchUnitFlag = st4PoSaSjService.saveBatch(suurList);
                    if(!batchUnitFlag){
                        return ResultVOUtil.error("1222","处理结果失败");
                    }
                }
                if(flag>0){
                    return ResultVOUtil.success();
                }
            }else{
                return ResultVOUtil.error("1222","处理结果失败");
            }
        }
        return ResultVOUtil.error("1222","处理结果失败");
    }
    @Override
    public ResultVO deleteUser(St4SysSa sa,St4SysSa seUser){
        sa.setSa007(0);
        if(st4SysSaMapper.updateById(sa)>0){
            return  ResultVOUtil.success();
        }

        return ResultVOUtil.error("1222","处理结果失败");
    }
}
