package com.gistone.bjhky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gistone.bjhky.entity.St4SysSa;
import com.gistone.bjhky.entity.St4SysSe;
import com.gistone.bjhky.entity.St4SysSh;
import com.gistone.bjhky.mapper.St4SysSeMapper;
import com.gistone.bjhky.mapper.St4SysShMapper;
import com.gistone.bjhky.service.ISt4SysSeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.bjhky.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 数据备份表 服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-09-04
 */
@Service
public class St4SysSeServiceImpl extends ServiceImpl<St4SysSeMapper, St4SysSe> implements ISt4SysSeService {
    @Autowired
    private St4SysSeMapper st4SysSeMapper;

    @Autowired
    private UrlsUtil urlsUtil;

    @Autowired
    private ConfigUtil configUtil;
    @Autowired
    private St4SysShMapper st4SysShMapper;

    @Override
    public Result listSeData(St4SysSe se) {


        Page<St4SysSe> page = new Page<>(se.getPageNumber(),se.getPageSize());
        IPage<St4SysSe> ipage = st4SysSeMapper.listDataBackUp(page,se);

        Result result = new Result();
        result.setStatus(1000);
        result.setMsg("加载成功");
        result.setRows(ipage.getRecords());
        result.setTotal((int)ipage.getTotal());
        result.setPage((int)ipage.getPages());

        return result;
    }
    //自动备份数据库
    @Override
    public void autoBackup() throws Exception {
        //备份名称
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime date = LocalDateTime.now();

        String filename = df.format(date) +".sql";

        String file = "D:\\bjhky\\UploadData\\sql";

                //urlsUtil.geturl() + "//sql";
        //1.备份
        Boolean res = DataBaseBackUp.exportDatabase(configUtil.getServerIp(),
                configUtil.getDPort(),
                configUtil.getDUserName(),
                configUtil.getDPassWord(),
                file, filename,
                configUtil.getDataBaseName());

        //2.插入记录到数据库
        if(res){
            St4SysSe data = new St4SysSe();
            data.setSe006(1);
            data.setSe002(filename);
            data.setSe005("upload/sql/" + filename);
            st4SysSeMapper.insert(data);
            System.out.println("sql备份成功");

        }

    }
    //手动备份数据库
    @Override
    public Result manualBackup(St4SysSe da, St4SysSa sysUser) {
        String fileUrl = "D:\\bjhky\\UploadData\\sql";

        try{
            Boolean res = DataBaseBackUp.exportDatabase(configUtil.getServerIp(),
                    configUtil.getDPort(), configUtil.getDUserName(), configUtil.getDPassWord(), fileUrl, da.getSe002() + ".sql", configUtil.getDataBaseName());
            if(res){
                if(st4SysSeMapper.insert(da)>0){
                    St4SysSh sh = new St4SysSh();
                    sh.setSh002(sysUser.getSa001());
                    sh.setSh003(LocalDateTime.now());
                    st4SysShMapper.insert(sh);
                    return Result.build(1000,"数据备份成功");
                }else {
                    return Result.build(1003,"数据备份成功,但是增加记录失败");
                }
            }else{
                return Result.build(1003, "手动备份数据库异常");
            }

        }catch (Exception e ){
            e.printStackTrace();
            return Result.build(1003, ResultMsg.MSG_1003);
        }

    }
    //查询备份数据库名是否存在
    @Override
    public List<St4SysSe> listByName(St4SysSe da){
        QueryWrapper<St4SysSe> wrapper = new QueryWrapper<>();
        wrapper.eq("SE002",da.getSe002());
        wrapper.eq("SE007",1);
        List<St4SysSe> list = st4SysSeMapper.selectList(wrapper);
        return list;
    }
}
