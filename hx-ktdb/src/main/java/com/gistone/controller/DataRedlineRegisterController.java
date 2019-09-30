package com.gistone.controller;


import com.gistone.entity.DataRedlineRegister;
import com.gistone.service.IDataRedlineRegisterService;
import com.gistone.util.ImportRedlineData;
import com.gistone.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器  红线块
 * </p>
 *
 * @author xjc
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/ktdb/dataRedlineRegister")
public class DataRedlineRegisterController {

    @Autowired
    private IDataRedlineRegisterService dataRedlineRegisterService;


    /**
     * 查询红线块
     *
     * @param requestData
     * @return
     */
    @RequestMapping(value = "listAll", method = RequestMethod.POST)
    public Object listAll(@RequestBody Map<String, Object> requestData) {

        DataRedlineRegister date = new DataRedlineRegister();

        List<Map<String, Object>> list = dataRedlineRegisterService.listAll(date);

        return Result.success(list);
    }

    /**
     * @return
     * @description: 导入红线数据
     * @param:
     * @author zf1017@foxmail.com
     * @date 2019/5/15 0015 16:24
     */
    @RequestMapping("/import")
    public void importRedline() {
        String path = "D:/Work/gistone/static/shapefile/redline_bk.shp";
        ImportRedlineData importRedlineData = new ImportRedlineData();
        ArrayList<DataRedlineRegister> lmMarkerMobiles = importRedlineData.readShapeFile(path);

        List<DataRedlineRegister> list = dataRedlineRegisterService.list(null);
        Iterator<DataRedlineRegister> iterator = null;
        if (lmMarkerMobiles != null && lmMarkerMobiles.size() > 0) {
            iterator = lmMarkerMobiles.iterator();
        }


        while (iterator.hasNext()) {
            DataRedlineRegister dataRedlineRegister = iterator.next();
            //过滤掉集合中已经有的数据
            for (DataRedlineRegister redlineRegister : list) {
                if (dataRedlineRegister.getSrldId() == redlineRegister.getSrldId()) {
                    //如果有重复的id则做更新
                    dataRedlineRegisterService.updateBy(dataRedlineRegister);
                    iterator.remove();
                }
            }
        }
        System.out.println(lmMarkerMobiles);
        boolean b = dataRedlineRegisterService.saveBatch(lmMarkerMobiles);
        System.out.println("======导入红线数据");

    }

    /**
     * @return
     * @description: 更新表中的某字段
     * @params
     * @author zf1017@foxmail.com
     * @date 2019/6/10 0010 10:16
     */
    @RequestMapping("/exchange")
    public void exchange() {
        List<DataRedlineRegister> dataRedlineRegisters = dataRedlineRegisterService.list(null);
        for (DataRedlineRegister dataRedlineRegister : dataRedlineRegisters) {
            if (dataRedlineRegister.getSrldTarget() == null) {
                if ("01".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("水源涵养");
                }
                if ("02".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("生物多样性维护");
                }
                if ("03".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("水土保持");
                }
                if ("04".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("防风固沙");
                }
                if ("05".equals(dataRedlineRegister.getTarget())) {
                    dataRedlineRegister.setSrldTarget("其他生态功能");
                }

                dataRedlineRegisterService.updateTarget(dataRedlineRegister.getSrldId(), dataRedlineRegister.getSrldTarget());

            }


        }

    }


}

