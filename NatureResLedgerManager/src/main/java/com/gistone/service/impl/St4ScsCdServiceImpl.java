package com.gistone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gistone.VO.ResultVO;
import com.gistone.entity.*;
import com.gistone.mapper.*;
import com.gistone.service.ISt4ScsCdService;
import com.gistone.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 问题点表 服务实现类
 * </p>
 *
 * @author zhaojingwei
 * @since 2019-08-13
 */
@Service
@Component
public class St4ScsCdServiceImpl extends ServiceImpl<St4ScsCdMapper, St4ScsCd> implements ISt4ScsCdService {

    @Autowired
    private St4ScsCdMapper st4ScsCdMapper;
    @Autowired
    private St4PoCdSaMapper st4PoCdSaMapper;
    @Autowired
    private St4ScsCkMapper st4ScsCkMapper;
    @Autowired
    private St4SysSaMapper st4SysSaMapper;
    @Autowired
    private ImageMapper imageMapper;

    @Value("${PATH}")
    private String PATH;


//    public ResultCp insertSpotDataFromApp (St4ScsCd id){
//        return null;
//    }

    @Override
    public Result listCheckPointToView(St4ScsCd data) {
        QueryWrapper<St4ScsCd> queryWrapper = new QueryWrapper<>();
        //保护地id
        if(data.getStrTime()!=null && data.getStrTime() != ""){
            data.setStrTime(data.getStrTime()+" 00:00:00");
        }
        if(data.getEndTime()!=null && data.getEndTime() != ""){
            data.setEndTime(data.getEndTime()+" 23:59:59");
        }
        queryWrapper.eq("SG001",data.getSg001());
        List<St4ScsCd> list = st4ScsCdMapper.listCheckPointToView(data);
        Result res = new Result();
        res.setData(list);
        res.setStatus(1000);
        res.setMsg("查询问题点位成功");
        return res;
    }
    @Override
    public Result listCheckPoint(St4ScsCd data, St4SysSa seUser) {
       /* QueryWrapper<St4ScsCd> queryWrapper = new QueryWrapper<>();
        //保护地id
        queryWrapper.eq("CD009",1);

        Page<St4ScsCd> page  = new Page<St4ScsCd>(data.getPageNumber(),data.getPageSize());
        IPage<St4ScsCd> ipage = st4ScsCdMapper.selectPage(page,queryWrapper);
        queryWrapper.eq("SG001",data.getSg001());
        List<St4ScsCd> list = ipage.getRecords();*/
        int size = data.getPageSize();//每页条数
        int number = data.getPageNumber();//开始索引
        int numberReal =0;
        if(0==number){
            numberReal = number;
        }else{
            numberReal= (number-1)*size;
        }
        Result res = new Result();
        data.setPageNumber(numberReal);
        data.setPageSize(size);
        //这里只能查看本单位下的问题点
        seUser = st4SysSaMapper.selectById(seUser);

        //下面这个用cd007方便查询避免实体类代码冗余cd007不是代表原来的意义而是代表了当前人的id
        data.setCd007(seUser.getSa001());
        if(seUser.getSa020()==0&&seUser.getSa001()!=1){
            data.setType(0);
            data.setSa01(seUser.getSa001());
            data.setSa02(seUser.getSa002());

        }else if(seUser.getSa020()==1) {
            data.setType(1);
            data.setSa01(seUser.getSa001());
        }else {
            data.setType(3);//避免默认值
        }
        List<St4ScsCd> list=st4ScsCdMapper.listCheckPoint(data);
        res.setRows(list);
        data.setPageNumber(null);
        data.setPageSize(null);
        int tsize = st4ScsCdMapper.listCheckPoint(data).size();
        res.setTotal(tsize);
        res.setStatus(1000);

        res.setPage((int)Math.ceil((double)tsize/size));
        res.setMsg("加载"+ ResultMsg.MSG_1000);
        return res;
    }


    @Override
    public Result insertCheckPoint(St4ScsCd cd) {
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCd002())){
            return Result.build(1001,"经度"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCd003())){
            return Result.build(1001,"纬度"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCd004())){
            return Result.build(1001,"编号"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getSg001())){
            return Result.build(1001,"保护区ID"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCl001())){
            return Result.build(1001,"批次任务ID"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getSd001())){
            return Result.build(1001,"所属行政区ID"+ResultMsg.MSG_1001);
        }
        if(!ObjectUtils.isNotNullAndEmpty(cd.getCl001())){
            return Result.build(1001,"批次任务ID"+ResultMsg.MSG_1001);
        }
        cd.setCd011(LocalDateTime.now());
        if(st4ScsCdMapper.insert(cd)>0){
            St4ScsCk data = new St4ScsCk();
            //问题点编号
            data.setCd004(cd.getCd004());
            //原始台账
            data.setCk088(0);
            //插入台账记录，后台添加问题点的时候默认插入一条空台账

            st4ScsCkMapper.insert(data);
            return Result.build(1000,"添加"+ ResultMsg.MSG_1000);
        }
        return Result.build(1005,ResultMsg.MSG_1005);
    }

    @Override
    public Result sysPointDataLd(Integer roleId, Integer uid) {
        //这里存在矛盾就是一个点被重复导入，和被重复提交到时候同步的时候取哪个活动设施为准 还有就是activityName为null的时候返回不了来了看看
        /**
         * 2019.10.8.1523这里考虑到在同一个任务下的同一个点是分给了多个人，而在每一个人同步的时候也要把其他人对这个点的核查状态也同步下来
         * sysPointAndLedgerDataLd方法原来是传递uid的现在改成cd001,因为往原sql里面传进去uid那样查询出来的只是一个人的信息，所以不满足
         * 这里的解决办法就是先把这个人所要核查的点全部去拿到传递给上面的方法这样就ok
         */
        QueryWrapper<St4PoCdSa> wrapper = new QueryWrapper<>();
        wrapper.eq("SA001",uid);
        List<St4PoCdSa> cdSas = st4PoCdSaMapper.selectList(wrapper);
        List<Integer> cds = cdSas.stream().map(St4PoCdSa::getCd001).collect(Collectors.toList());

        HashSet h = new HashSet(cds);
        cds.clear();

        cds.addAll(h);
        Result res = new Result();
        if(cds!=null&&cds.size()>0){
            List<St4ScsCd> list = st4ScsCdMapper.sysPointAndLedgerDataLd(cds);
            for(int i = 0 ; i < list.size() ; i ++){
                //将坐标格式转为经纬度
                list.get(i).setCd002(PointHelp.Dms2D(list.get(i).getCd002()));
                list.get(i).setCd003(PointHelp.Dms2D(list.get(i).getCd003()));
                //如果核查台账的建设时间是"0001-00-00 00:00:00"，则说明移动端填写的是不详，返回的时候将数据修改为null
           /* if(list.get(i).getSt4ScsCkList().size() > 1 && list.get(i).getSt4ScsCkList().get(1).getCk020() != null && list.get(i).getSt4ScsCkList().get(1).getCk020().equals("0001-00-00 00:00:00")){
                list.get(i).getSt4ScsCkList().get(1).setCk020(null);
            }*/
                //cse
            }
            res.setData(list);
        }else{
            List<St4ScsCd> list = new ArrayList<>();
            res.setData(list);
        }
        res.setStatus(1000);
        res.setMsg("同步问题点位成功");
        return res;
    }


    public ResultVO sysSpotData(Integer id){
        try {
            List<St4ScsCd> cdList = st4ScsCdMapper.sysSpotData(id);
            ResultCp cp = new ResultCp();
            cp.setData(cdList);
            return ResultVOUtil.success(cp);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(ResultEnum.HANDLEFAIL.getCode(), "同步数据失败，服务器异常！");
        }
    }


    @Override
    public Map<String, Object> list(Integer pageNum, Integer pageSize, Integer id) {
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("image_id",id);
        IPage<St4ScsCd> iPage = st4ScsCdMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", iPage.getRecords());
        result.put("total", iPage.getTotal());
        return result;
    }
    public List list2(Integer id) {
        QueryWrapper<St4ScsCd> wrapper = new QueryWrapper<>();
        wrapper.eq("image_id",id);
        return st4ScsCdMapper.selectList(wrapper);
    }

    @Override
    public void delete(List<Integer> ids) {
        //具体逻辑

    }

    @Override
    public void insert(List<Map<String, Object>> data,Integer imageId,Integer createBy) {
        //通过影像id先删除记录然后再插入,然后再写入shp文件，将地址更新到影像表中！
        st4ScsCdMapper.delete(new QueryWrapper<St4ScsCd>().eq("image_id",imageId));
        //从data中构造属性
        for (Map<String, Object> datum : data) {
            Map<String,Object> attributes = (Map<String, Object>) datum.get("attributes");
            //通过属性构造参数
            St4ScsCd iterpretation = new St4ScsCd();
            if(null!=attributes.get("activeName")){
                iterpretation.setActiveName(attributes.get("activeName")+"");
            }
            if(null!=attributes.get("activeType")){
                iterpretation.setActiveType(attributes.get("activeType")+"");
            }
            if(null!=attributes.get("area")){
                iterpretation.setArea(attributes.get("area")+"");
            }
            if(null!=attributes.get("descri")){
                iterpretation.setDescri(attributes.get("descri")+"");
            }
            if(null!=attributes.get("remark")){
                iterpretation.setCd012(attributes.get("remark")+"");
            }
            Map<String,Object> rings = (Map<String, Object>) datum.get("geometry");
            iterpretation.setGeometry(rings.get("rings")+"");
            iterpretation.setImageId(imageId);
            iterpretation.setCd010(createBy);
            iterpretation.setCd011(LocalDateTime.now());
            st4ScsCdMapper.insert(iterpretation);
        }
        //执行写入shp文件操作，返回的地址插入到影像表中
        String url = PathUtile.getRandomPath(PATH+"/epr/image/","x.shp");
        String res = ShpUtil.handleWebData(JSONArray.parseArray(net.sf.json.JSONArray.fromObject(data)+""),url);
        if("0".equals(res)){
            Image image = new Image();
            image.setId(imageId);
            image.setShpurl(url.split(":")[1]);
            imageMapper.updateById(image);
        }
    }



    @Override
    public void edit(St4ScsCd entity) {
        //具体逻辑
    }



}
