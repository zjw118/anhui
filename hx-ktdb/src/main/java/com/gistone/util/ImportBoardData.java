package com.gistone.util;

import com.gistone.entity.LmBoard;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zf1017@foxmail.com
 * @date 2019/5/15 0015 13:42
 * @description
 */
public class ImportBoardData {
    public ArrayList<LmBoard> readShapeFile(String filePath) {
        ArrayList<LmBoard> modelList = new ArrayList<>();
        File folder = new File(filePath);
        if (!folder.isDirectory()) {
            if (folder.toString().endsWith(".shp")) {
                try {
                    modelList = getShapeFile(folder);
                    return modelList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("选择的文件后缀名不是.shp");
            }
        }else{
            File[] files = folder.listFiles();
            if (!(files.length > 0)) {
                System.out.println("目录文件为空");
                return modelList;
            }

            for (File file : files) {
                if (!file.toString().endsWith(".shp")) {
                    continue;
                }
                try {
                    ArrayList<LmBoard> models = getShapeFile(file);
                    modelList.addAll(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelList;
    }

    private ArrayList<LmBoard> getShapeFile(File file) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", file.toURI().toURL());
        ArrayList<LmBoard> models = new ArrayList<>();
        DataStore dataStore = DataStoreFinder.getDataStore(map);
        //字符转码，防止中文乱码
        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            LmBoard model = new LmBoard();
            Iterator<? extends Property> iterator = feature.getValue().iterator();
            while (iterator.hasNext()) {
                Property property = iterator.next();
//                System.out.println(property);
                //property数据与实体类对应
//                double b= Double.parseDouble(property.getValue().toString());
//                int a = (int) b;
                if(property.getName().toString().equals("longitude")){
                    model.setLongitude(Double.parseDouble(property.getValue().toString()));
                }
                if(property.getName().toString().equals("latitude")){
                    model.setLatitude(Double.parseDouble(property.getValue().toString()));
                }
//                if(property.getName().toString().equals("redline_id")){
//                    model.setRedlineId(Integer.parseInt(property.getValue().toString()));
//                }
//                if(property.getName().toString().equals("hxcode")){
//                    model.setRedlineNum(property.getValue().toString());
//                }
                if(property.getName().toString().equals("pac")){
                    model.setCode(property.getValue().toString());
                }


            }
            models.add(model);
        }
        return models;
    }

    public static void main(String[] args) {
        try {
            String path = "D:/Work/gistone/文档/祁连山生态红线/祁连山山水林田湖生态修复试点/Export_Output_4.shp";
            ImportBoardData importRedlineData = new ImportBoardData();
            ArrayList<LmBoard> lmMarkerMobiles = importRedlineData.readShapeFile(path);
//            boolean b = dataRedlineRegisterService.saveBatch(lmMarkerMobiles);
            lmMarkerMobiles.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
