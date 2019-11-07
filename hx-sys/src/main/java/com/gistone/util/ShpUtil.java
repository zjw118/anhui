package com.gistone.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gistone.VO.ResultVO;
import com.vividsolutions.jts.geom.*;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;


/**
 * @author zf1017@foxmail.com
 * @date 2019/9/18 0018 10:36
 * @description 读取shp文件返回json数据
 */
@Slf4j
public class ShpUtil {
    /**
     * @param filePath
     * @return
     * @description 读取shp文件返回json数据, type 1面 2点
     */
    public static List<String> readShapeFileToStr(String filePath, Integer type) {
        String result = "";
        List<String> list = new ArrayList<>();
        try {
            FeatureJSON fjson = new FeatureJSON();
            StringBuffer fsb = new StringBuffer();
            JSONArray array = new JSONArray();

            File file = new File(filePath);
            fsb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            ShapefileDataStore shpDataStore = null;
            shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            //设置字符编码
            Charset charset = Charset.forName("UTF-8");
            shpDataStore.setCharset(charset);
            //获取图层名称
            String typeName = shpDataStore.getTypeNames()[0];
            //数据读取
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection sfc = featureSource.getFeatures();
            SimpleFeatureIterator itertor = sfc.features();
            while (itertor.hasNext()) {
                SimpleFeature feature = itertor.next();
                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                JSONObject json = (JSONObject) JSONObject.parse(writer.toString());
                array.add(json);
            }
            for (int i = 0; i < array.size(); i++) {
                Map<String, String> map2 = new HashMap<>();
                //获取每一个JsonObject对象
                JSONObject myjObject = array.getJSONObject(i);
                //获取geometry属性
                JSONObject maps = (JSONObject) myjObject.get("geometry");
                //获取坐标点
                String arrayList = maps.get("coordinates").toString().substring(1, maps.get("coordinates").toString().length() - 1);
                //封装数据
                if (type.equals(1)) {
                    map2.put("type", "polygon");
                    JSONArray corarray = maps.getJSONArray("coordinates");
                    JSONArray json = new JSONArray();
                    if (corarray.size() > 1) {
                        for (int j = 0; j < corarray.size(); j++) {
                            json.add(corarray.getJSONArray(j).getJSONArray(0));
                        }
                        map2.put("rings", json.toJSONString());
                    } else {
                        map2.put("rings", arrayList);
                    }
                } else {
                    map2.put("type", "point");
                    map2.put("rings", arrayList);
                }
                //获取属性
                Object map1 = myjObject.getJSONObject("properties");
                Map<String, String> map11 = (Map) JSON.parse(((JSONObject) map1).toString());
                //构建返回
                Map<String, String> stringObjectMap = new HashMap<>();
                stringObjectMap.put("attributes", net.sf.json.JSONObject.fromObject(map11) + "");
                stringObjectMap.put("geometry", net.sf.json.JSONObject.fromObject(map2) + "");
                list.add(net.sf.json.JSONObject.fromObject(stringObjectMap) + "");
            }
            itertor.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return list;

    }

    public static void writePoint(List<Map<String, Object>> data, String filePath) {
        try {
            Map<String, Object> map1 = data.get(0);
            Map<String, Object> attributes = (Map<String, Object>) map1.get("attributes");

            Set<Map.Entry<String, Object>> entries = attributes.entrySet();
            StringBuilder stringBuilder = new StringBuilder("");

            for (Map.Entry<String, Object> entry : entries) {
                stringBuilder.append(entry.getKey()).append(":String").append(",");
            }
            String properties = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
            SimpleFeatureType TYPE = DataUtilities.createType("Location",
                    "the_geom:Point:srid=3857," + // <- the geometry attribute: Point type
                            properties
            );

            List<SimpleFeature> features = new ArrayList<>();
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
            //构造feature
            for (Map<String, Object> datum : data) {
                Map<String, Object> geometry = (Map<String, Object>) datum.get("geometry");
                Double x = (Double) geometry.get("x");
                Double y = (Double) geometry.get("y");
                Point point = geometryFactory.createPoint(new Coordinate(x, y));
                featureBuilder.add(point);
                //构造属性
                Map<String, Object> attribute = (Map<String, Object>) datum.get("attributes");

                Set<Map.Entry<String, Object>> entrie = attribute.entrySet();

                for (Map.Entry<String, Object> entry : entrie) {
                    featureBuilder.add(entry.getValue());
                }

                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }

            File newFile = new File(filePath);

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);

            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setCharset(Charset.forName("UTF-8"));

            Transaction transaction;
            String typeName;
            SimpleFeatureSource featureSource;
            SimpleFeatureType SHAPE_TYPE;
            newDataStore.createSchema(TYPE);

            transaction = new DefaultTransaction("create");

            typeName = newDataStore.getTypeNames()[0];
            featureSource = newDataStore.getFeatureSource(typeName);
            SHAPE_TYPE = featureSource.getSchema();

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    System.out.println("finish");
                    transaction.commit();
                } catch (Exception problem) {

                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();

                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception problem) {
            log.error(problem.getMessage());
        }

    }

    public static void writeAfterRead(List<Map<String, Object>> data, String filePath) {
//        String filepath = "D:/DevelopTools/sheapf.shp";
        try {

          /*  SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName("Location");
            builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system

            // add attributes in order
            builder.add("the_geom", Point.class);
            builder.length(15).add("Name", Map.class); // <- 15 chars width for name field
            builder.add("number",Integer.class);

            // build the type
            final SimpleFeatureType LOCATION = builder.buildFeatureType();*/
            //TODO 动态拼接字段

            SimpleFeatureType TYPE = null;
            List<SimpleFeature> features = new ArrayList<>();
            //构造feature
            for (Map<String, Object> datum : data) {
                // Map<String, Object> map1 = data.get(0);
                //获取属性，准备拼接要写入shp的属性
                Map<String, Object> attributes = (Map<String, Object>) datum.get("attributes");
                //遍历map，获取属性值
                Set<Map.Entry<String, Object>> entries = attributes.entrySet();
                StringBuilder stringBuilder = new StringBuilder("");
                for (Map.Entry<String, Object> entry : entries) {
                    stringBuilder.append(entry.getKey()).append(":String").append(",");
                }
                String properties = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                TYPE = DataUtilities.createType("Location",
                        "the_geom:MultiPolygon:srid=3857," + properties
                );


                GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
                //获取
                Map<String, Object> geometry = (Map<String, Object>) datum.get("geometry");
                List<List<List<BigDecimal>>> lists = (List<List<List<BigDecimal>>>) geometry.get("rings");
                System.out.println(lists.size());
                List<Polygon> polygonList = new ArrayList<>();
                Polygon polygon = null;
                Coordinate[] coordinatesArray = null;
                for (int x = 0; x < lists.size(); x++) {
                    System.out.println("--坐标数组长度--" + lists.get(x).size());
                    coordinatesArray = new Coordinate[lists.get(x).size()];
                    for (int i = 0; i < lists.get(x).size(); i++) {
                        Double lon = 0.0;
                        Double y = 0.0;
                        for (int j = 0; j < lists.get(x).get(i).size(); j++) {
                            if (j == 0) {
                                System.out.println(lists.get(x).get(i).get(j));
                                lon = Double.parseDouble(lists.get(x).get(i).get(j).toPlainString());
                            } else {
                                y = Double.parseDouble(lists.get(x).get(i).get(j).toPlainString());
                            }
                        }
                        Coordinate coordinate = new Coordinate(lon, y);
                        coordinatesArray[i] = coordinate;
                    }

                }
                polygon = geometryFactory.createPolygon(coordinatesArray);

                Polygon[] polygons = {polygon};

                //构造坐标点
                LinearRing linearRing = geometryFactory.createLinearRing(coordinatesArray);

                MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons);
                featureBuilder.add(multiPolygon);

                //构造属性
                Map<String, Object> attribute = (Map<String, Object>) datum.get("attributes");

                Set<Map.Entry<String, Object>> entrie = attribute.entrySet();

                for (Map.Entry<String, Object> entry : entrie) {
                    featureBuilder.add(entry.getValue());
                }


                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }
            /*
             * GeometryFactory will be used to create the geometry attribute of each feature,
             * using a Point object for the location.
             */


//            Point point = geometryFactory.createPoint(new Coordinate(111.159, 23.5568));


            File newFile = new File(filePath);

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);

            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setCharset(Charset.forName("UTF-8"));
            /*
             * TYPE is used as a template to describe the file contents
             */
            Transaction transaction;
            String typeName;
            SimpleFeatureSource featureSource;
            SimpleFeatureType SHAPE_TYPE;

            newDataStore.createSchema(TYPE);

            transaction = new DefaultTransaction("create");

            typeName = newDataStore.getTypeNames()[0];
            featureSource = newDataStore.getFeatureSource(typeName);
            SHAPE_TYPE = featureSource.getSchema();

            /*
             * The Shapefile format has a couple limitations:
             * - "the_geom" is always first, and used for the geometry attribute name
             * - "the_geom" must be of type Point, MultiPoint, MuiltiLineString, MultiPolygon
             * - Attribute names are limited in length
             * - Not all data types are supported (example Timestamp represented as Date)
             *
             * Each data store has different limitations so check the resulting SimpleFeatureType.
             */
            System.out.println("SHAPE:" + SHAPE_TYPE);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                /*
                 * SimpleFeatureStore has a method to add features from a
                 * SimpleFeatureCollection object, so we use the ListFeatureCollection
                 * class to wrap our list of features.
                 */
                SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    System.out.println("finish");
                    transaction.commit();
                } catch (Exception problem) {

                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();

                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception problem) {
            log.error(problem.getMessage());
        }

    }

    /**
     * 前端页面来源的json数据处理
     *
     * @param array ：JSONArray格式数据
     * @return
     */
    public static String handleWebData(JSONArray array, String filePath) {
        try {
            final SimpleFeatureType TYPE = createFeatureType();
            //在我们创建功能时收集功能的列表
            List<SimpleFeature> features = new ArrayList<>();
            //geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

            for (int i = 0; i < array.size(); i++) {
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
                JSONObject jobj = (JSONObject) array.get(i);
                JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("rings");
                if (zhj.size() > 1) {
                    //大于1说明在一条记录中有两个面，需要进行判断
                    //根据面的数量先创建数组，用于最终比较
                    List<Coordinate[]> coords = new ArrayList<>();
                    for (int j = 0; j < zhj.size(); j++) {
                        Coordinate[] coor = new Coordinate[zhj.getJSONArray(j).size()];
                        for (int k = 0; k < zhj.getJSONArray(j).size(); k++) {
                            coor[k] = new Coordinate(zhj.getJSONArray(j).getJSONArray(k).getDouble(0), zhj.getJSONArray(j).getJSONArray(k).getDouble(1));
                        }
                        coords.add(coor);
                    }
                    Polygon[] polygon = new Polygon[coords.size()];
                    for (int j = 0; j < coords.size(); j++) {
                        polygon[j] = geometryFactory.createPolygon(coords.get(j));
                    }
                    MultiPolygon mp = geometryFactory.createMultiPolygon(polygon);
                    featureBuilder.add(mp);
                } else {
                    //根据实际坐标的多少创建数组
                    Coordinate[] coords = new Coordinate[zhj.getJSONArray(0).size()];
                    //跳一级循环，直接获取最终的坐标数组
                    for (int k = 0; k < zhj.getJSONArray(0).size(); k++) {
                        coords[k] = new Coordinate(zhj.getJSONArray(0).getJSONArray(k).getDouble(0), zhj.getJSONArray(0).getJSONArray(k).getDouble(1));
                    }
                    //构造具有给定外部边界的多边形
                    Polygon polygon = geometryFactory.createPolygon(coords);
                    featureBuilder.add(polygon);
                }


                //这里按顺序添加属性
                featureBuilder.add(jobj.getJSONObject("attributes").getString("area"));
                featureBuilder.add(jobj.getJSONObject("attributes").getIntValue("ObjectID"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("center"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("name"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("position"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("type"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("region"));
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }


            //创建shp文件并写入数据
            if (createSHP(TYPE, features, new File(filePath)).equals("0")) {
                System.out.println("文件保存成功");
            }

        } catch (Exception e) {
            System.out.println("web传入来源数据处理错误");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }


    /**
     * @param array
     * @param filePath
     * @return java.lang.String
     * @description:写预设红线数据到shp
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 11:04
     */
    public static String importPreRedlinedata(JSONArray array, String filePath) {
        try {
            final SimpleFeatureType TYPE = createFeatureTypeForRedline();
            //在我们创建功能时收集功能的列表
            List<SimpleFeature> features = new ArrayList<>();
            //geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

            for (int i = 0; i < array.size(); i++) {
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
                JSONObject jobj = (JSONObject) array.get(i);
                JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("rings");
                if (zhj.size() > 1) {
                    //大于1说明在一条记录中有两个面，需要进行判断
                    //根据面的数量先创建数组，用于最终比较
                    List<Coordinate[]> coords = new ArrayList<>();
                    for (int j = 0; j < zhj.size(); j++) {
                        Coordinate[] coor = new Coordinate[zhj.getJSONArray(j).size()];
                        for (int k = 0; k < zhj.getJSONArray(j).size(); k++) {
                            coor[k] = new Coordinate(zhj.getJSONArray(j).getJSONArray(k).getDouble(0), zhj.getJSONArray(j).getJSONArray(k).getDouble(1));
                        }
                        coords.add(coor);
                    }
                    Polygon[] polygon = new Polygon[coords.size()];
                    for (int j = 0; j < coords.size(); j++) {
                        polygon[j] = geometryFactory.createPolygon(coords.get(j));
                    }
                    MultiPolygon mp = geometryFactory.createMultiPolygon(polygon);
                    featureBuilder.add(mp);
                } else {
                    //根据实际坐标的多少创建数组
                    Coordinate[] coords = new Coordinate[zhj.getJSONArray(0).size()];
                    //跳一级循环，直接获取最终的坐标数组
                    for (int k = 0; k < zhj.getJSONArray(0).size(); k++) {
                        coords[k] = new Coordinate(zhj.getJSONArray(0).getJSONArray(k).getDouble(0), zhj.getJSONArray(0).getJSONArray(k).getDouble(1));
                    }
                    //构造具有给定外部边界的多边形
                    Polygon polygon = geometryFactory.createPolygon(coords);
                    featureBuilder.add(polygon);
                }

                //这里按顺序添加属性

//                featureBuilder.add(jobj.getJSONObject("attributes").getIntValue("FID"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("行政区代码"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("面积"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("编号"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("类型").substring(0,2));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("类型").substring(2));
                featureBuilder.add(jobj.getJSONObject("attributes").getIntValue("OBJECTID"));
                //featureBuilder.add(jobj.getJSONObject("attributes").getString("center"));
//                featureBuilder.set("center",jobj.getJSONObject("attributes").getString("center"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("peopleNum"));
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }


            //创建shp文件并写入数据
            if (createSHP(TYPE, features, new File(filePath)).equals("0")) {
                System.out.println("文件保存成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入shp异常，异常信息为：", e.getMessage());
            System.out.println("web传入来源数据处理错误");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }

    /**
     * @param array
     * @param filePath
     * @return java.lang.String
     * @description:写预设界桩数据到shp
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 16:28
     */
    public static String importPreMarkerdata(JSONArray array, String filePath) {
        try {
            final SimpleFeatureType TYPE = createFeatureTypeForMarker();
            //在我们创建功能时收集功能的列表
            List<SimpleFeature> features = new ArrayList<>();
            //geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

            for (int i = 0; i < array.size(); i++) {
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
                JSONObject jobj = (JSONObject) array.get(i);
                JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("rings");
                if(zhj!=null){


                if (zhj.size() > 1) {
                    //大于1说明在一条记录中有两个面，需要进行判断
                    //根据面的数量先创建数组，用于最终比较
                    List<Coordinate[]> coords = new ArrayList<>();
                    for (int j = 0; j < zhj.size(); j++) {
                        Coordinate[] coor = new Coordinate[zhj.getJSONArray(j).size()];
                        for (int k = 0; k < zhj.getJSONArray(j).size(); k++) {
                            coor[k] = new Coordinate(zhj.getJSONArray(j).getJSONArray(k).getDouble(0), zhj.getJSONArray(j).getJSONArray(k).getDouble(1));
                        }
                        coords.add(coor);
                    }
                    Polygon[] polygon = new Polygon[coords.size()];
                    for (int j = 0; j < coords.size(); j++) {
                        polygon[j] = geometryFactory.createPolygon(coords.get(j));
                    }
                    MultiPolygon mp = geometryFactory.createMultiPolygon(polygon);
                    featureBuilder.add(mp);
                } else {
                    //根据实际坐标的多少创建数组
                    Coordinate[] coords = new Coordinate[zhj.getJSONArray(0).size()];
                    //跳一级循环，直接获取最终的坐标数组
                    for (int k = 0; k < zhj.getJSONArray(0).size(); k++) {
                        coords[k] = new Coordinate(zhj.getJSONArray(0).getJSONArray(k).getDouble(0), zhj.getJSONArray(0).getJSONArray(k).getDouble(1));
                    }
                    //构造具有给定外部边界的多边形
                    Polygon polygon = geometryFactory.createPolygon(coords);
                    featureBuilder.add(polygon);
                }
                }else{
                    Coordinate coordinate = new Coordinate(jobj.getJSONObject("geometry").getDoubleValue("X"),jobj.getJSONObject("geometry").getDoubleValue("Y"));
                    Point point = geometryFactory.createPoint(coordinate);
                    featureBuilder.add(point);
                }
                //这里按顺序添加属性

                featureBuilder.add(jobj.getJSONObject("attributes").getString("编号"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("pac"));
                featureBuilder.add(jobj.getJSONObject("attributes").getDoubleValue("X坐标"));
                featureBuilder.add(jobj.getJSONObject("attributes").getDoubleValue("Y坐标"));
                featureBuilder.add(jobj.getJSONObject("attributes").getIntValue("redline_id"));
//                featureBuilder.add(jobj.getJSONObject("attributes").getString("attribute"));
                //featureBuilder.add(jobj.getJSONObject("attributes").getString("center"));
//                featureBuilder.set("center",jobj.getJSONObject("attributes").getString("center"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("peopleNum"));
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }


            //创建shp文件并写入数据
            if (createSHP(TYPE, features, new File(filePath)).equals("0")) {
                System.out.println("文件保存成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入shp异常，异常信息为：", e.getMessage());
            System.out.println("web传入来源数据处理错误");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }


    public static String importPreBoarddata(JSONArray array, String filePath) {
        try {
            final SimpleFeatureType TYPE = createFeatureTypeForBoard();
            //在我们创建功能时收集功能的列表
            List<SimpleFeature> features = new ArrayList<>();
            //geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

            for (int i = 0; i < array.size(); i++) {
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
                JSONObject jobj = (JSONObject) array.get(i);
                JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("rings");
                if(zhj!=null){


                if (zhj.size() > 1) {
                    //大于1说明在一条记录中有两个面，需要进行判断
                    //根据面的数量先创建数组，用于最终比较
                    List<Coordinate[]> coords = new ArrayList<>();
                    for (int j = 0; j < zhj.size(); j++) {
                        Coordinate[] coor = new Coordinate[zhj.getJSONArray(j).size()];
                        for (int k = 0; k < zhj.getJSONArray(j).size(); k++) {
                            coor[k] = new Coordinate(zhj.getJSONArray(j).getJSONArray(k).getDouble(0), zhj.getJSONArray(j).getJSONArray(k).getDouble(1));
                        }
                        coords.add(coor);
                    }
                    Polygon[] polygon = new Polygon[coords.size()];
                    for (int j = 0; j < coords.size(); j++) {
                        polygon[j] = geometryFactory.createPolygon(coords.get(j));
                    }
                    MultiPolygon mp = geometryFactory.createMultiPolygon(polygon);
                    featureBuilder.add(mp);
                } else {
                        //根据实际坐标的多少创建数组
                        Coordinate[] coords = new Coordinate[zhj.getJSONArray(0).size()];
                        //跳一级循环，直接获取最终的坐标数组
                        for (int k = 0; k < zhj.getJSONArray(0).size(); k++) {
                            coords[k] = new Coordinate(zhj.getJSONArray(0).getJSONArray(k).getDouble(0), zhj.getJSONArray(0).getJSONArray(k).getDouble(1));
                        }
                        //构造具有给定外部边界的多边形
                        Polygon polygon = geometryFactory.createPolygon(coords);
                        featureBuilder.add(polygon);
                    }
                }else{
                    Coordinate coordinate = new Coordinate(jobj.getJSONObject("geometry").getDoubleValue("X"),jobj.getJSONObject("geometry").getDoubleValue("Y"));
                    Point point = geometryFactory.createPoint(coordinate);
                    featureBuilder.add(point);
                }

                //这里按顺序添加属性


                featureBuilder.add(jobj.getJSONObject("attributes").getDoubleValue("X坐标"));
                featureBuilder.add(jobj.getJSONObject("attributes").getDoubleValue("Y坐标"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("hxcode"));
                featureBuilder.add(jobj.getJSONObject("attributes").getIntValue("redline_id"));
                featureBuilder.add(jobj.getJSONObject("attributes").getString("编号"));
//                featureBuilder.add(jobj.getJSONObject("attributes").getString("attribute"));
                //featureBuilder.add(jobj.getJSONObject("attributes").getString("center"));
//                featureBuilder.set("center",jobj.getJSONObject("attributes").getString("center"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("peopleNum"));
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }


            //创建shp文件并写入数据
            if (createSHP(TYPE, features, new File(filePath)).equals("0")) {
                System.out.println("文件保存成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入shp异常，异常信息为：", e.getMessage());
            System.out.println("web传入来源数据处理错误");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }
    /**
     * 创建shp文件并写入数据
     *
     * @param TYPE：         空间数据模板
     * @param features：数据内容
     * @return
     */
    private static String createSHP(SimpleFeatureType TYPE, List<SimpleFeature> features, File newFile) {
        try {
            //从FeatureCollection创建shapefile
            //DataStoreFactory与参数一起使用表示我们想要空间索引
            //使用createSchema(SimpleFeatureType)方法来设置shapefile
            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            Map<String, Serializable> params = new HashMap<>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);

            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            //设置字符编码
            Charset charset = Charset.forName("UTF-8");
            newDataStore.setCharset(charset);

            //TYPE被用作描述文件内容的模板
            newDataStore.createSchema(TYPE);

            //将特征数据写入shapefile
            //在SimpleFeatureStore我们使用要做到这一点需要一个FeatureCollection对象，所以我们总结我们的一个功能列表ListFeatureCollection。
            //使用transaction.commit()安全地一次性写出功能
            Transaction transaction = new DefaultTransaction("create");

            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
            SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();

            System.out.println("SHAPE:" + SHAPE_TYPE);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
                //System.exit(0); // success!
            } else {
                System.out.println(typeName + " 不支持读/写访问");
                // System.exit(1);
            }

        } catch (Exception e) {
            System.out.println("文件生成报错");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }




    /**
     * 定义空间数据模板
     *
     * @return
     */
    private static SimpleFeatureType createFeatureType() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系


        // 按顺序添加属性
        builder.add("the_geom", MultiPolygon.class);//必须放最上面

        builder.length(100).add("area", String.class);
        builder.add("ObjectID", Integer.class);
        builder.length(100).add("center", String.class);
        builder.length(100).add("name", String.class);// 字段的宽度为100个字符
        builder.length(100).add("position", String.class);
        builder.length(100).add("type", String.class);
        builder.length(100).add("region", String.class);



        // 生成类型
        final SimpleFeatureType LOCATION = builder.buildFeatureType();
        return LOCATION;
    }

    /**
     * @param
     * @return org.opengis.feature.simple.SimpleFeatureType
     * @description:导入预设红线数据模板
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/29 0029 17:03
     */
    private static SimpleFeatureType createFeatureTypeForRedline() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();

        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系
        // 按顺序添加属性
        builder.add("the_geom", MultiPolygon.class);
//        builder.add("FID", Integer.class);
        //  builder.length(100).add("puct", String.class);// 字段的宽度为100个字符
        // builder.length(100).add("type", String.class);
        // builder.length(100).add("region", String.class);
        // builder.length(100).add("position", String.class);

//        String area = new String("面积".getBytes("ISO-8859-1"), "UTF-8");
        builder.length(100).add("pac", String.class);
        builder.length(100).add("area", String.class);

        builder.length(100).add("hxcode", String.class);
        builder.length(100).add("type", String.class);
        builder.length(100).add("attribute", String.class);
        builder.length(100).add("Id", Integer.class);


        // 生成类型
        final SimpleFeatureType LOCATION = builder.buildFeatureType();
        return LOCATION;
    }

    /**
     * @param
     * @return org.opengis.feature.simple.SimpleFeatureType
     * @description:导入预设界桩数据模板
     * @author zf1017@foxmail.com
     * @motto: Talk is cheap,show me the code
     * @date 2019/10/30 0030 14:31
     */
    private static SimpleFeatureType createFeatureTypeForMarker() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系
        // 按顺序添加属性
        builder.add("the_geom", MultiPolygon.class);
        builder.add("jzcode", String.class);
        builder.length(100).add("pac", String.class);
        builder.length(100).add("longitude", Double.class);
        builder.length(100).add("latitude", Double.class);
        builder.length(100).add("redline_id", Integer.class);


        // 生成类型
        final SimpleFeatureType LOCATION = builder.buildFeatureType();
        return LOCATION;
    }

    private static SimpleFeatureType createFeatureTypeForBoard() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系
        // 按顺序添加属性
        builder.add("the_geom", MultiPolygon.class);
        builder.length(100).add("longitude", Double.class);
        builder.length(100).add("latitude", Double.class);
        builder.length(100).add("hxcode", String.class);
        builder.length(100).add("redline_id", Integer.class);
        builder.length(100).add("number",String.class);


        // 生成类型
        final SimpleFeatureType LOCATION = builder.buildFeatureType();
        return LOCATION;
    }

    /**
     * 对从shp文件读取到的数据进行处理
     *
     * @param array：空间几何图形
     * @return
     */
    public static String handleSHPData(JSONArray array) {
        try {
            System.out.println(array.toString());

            final SimpleFeatureType TYPE = createFeatureType();
            //在我们创建功能时收集功能的列表
            List<SimpleFeature> features = new ArrayList<>();
            //geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            for (int i = 1; i < array.size(); i++) {
                JSONObject jobj = (JSONObject) array.get(i);
                JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("coordinates");
                JSONArray lin = new JSONArray();
                for (int j = 0; j < zhj.size(); j++) {
                    for (int k = 0; k < zhj.getJSONArray(j).size(); k++) {
                        for (int l = 0; l < zhj.getJSONArray(j).getJSONArray(k).size(); l++) {
                            lin.add(zhj.getJSONArray(j).getJSONArray(k).getJSONArray(l));
                        }
                    }
                }

                Coordinate[] coords = new Coordinate[lin.size()];
                for (int j = 0; j < lin.size(); j++) {
                    coords[j] = new Coordinate(lin.getJSONArray(j).getDouble(0), lin.getJSONArray(j).getDouble(1));
                }

                Polygon polygon = geometryFactory.createPolygon(coords);
                featureBuilder.add(polygon);
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }

            //创建shp文件并写入数据
            if (createSHP(TYPE, features, new File("E:\\3857\\111111.shp")).equals("0")) {
                System.out.println("文件保存成功");
            }

        } catch (Exception e) {
            System.out.println("文件来源数据处理错误");
            System.out.println(e.getMessage());
            return "1";
        }
        return "0";
    }

    public static Object getSHP(@RequestBody String shpPath) {
        ResultVO result = new ResultVO();

        //存储坐标数组
        JSONArray array = new JSONArray();

        try {
            //定义shp文件的绝对路径
            File file = new File(shpPath);

            FeatureJSON fjson = new FeatureJSON();
            StringBuffer fsb = new StringBuffer();
            fsb.append("{\"type\": \"FeatureCollection\",\"features\": ");

            //读取shp文件内容
            Map<String, Serializable> params = new HashMap<>();
            params.put("url", file.toURI().toURL());
            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            Charset charset = Charset.forName("UTF-8");
            newDataStore.setCharset(charset);
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection collection = featureSource.getFeatures();
            SimpleFeatureIterator features = collection.features();

            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                JSONObject json = (JSONObject) JSONObject.parse(writer.toString());
                array.add(json);
            }
            features.close();
            fsb.append(array.toString());
            fsb.append("}");
            System.out.println(array.toString());

            result.setMsg("ok");
            result.setData(fsb);
            //result.setStatus(Status.SUCCESS);
        } catch (Exception e) {
            System.out.println("读取SHP文件异常");
            System.out.println(e.getMessage());
            //result.setStatus(Status.SYSTEM_EXCEPTION);
            result.setMsg("系统异常");
        }

        //对文件来源的空间数据进行处理
//		if(handleSHPData(array).equals("0")){
//			System.out.println("数据处理成功");
//		}
        //对前端返回的的空间数据进行处理
     /*   if(handleWebData(array,shpPath).equals("0")){
            System.out.println("数据处理成功");
        }*/
        return result;
    }

    public static void main(String[] args) {
        try {
            String path = "D:/Work/gistone/文档/生态工程/3857/ld.shp";
//            ReadShapeFile readShapeFile = new ReadShapeFile();
//            Object s = readShapeFile.readShapeFileToStr(path);

            List<Map<String, Object>> list = new ArrayList<>();
//            readShapeFile.write(list);
//            Map maps = (Map) JSON.parse(s);
            /*ArrayList<LmMarkerMobile> lmMarkerMobiles = readShapeFile.readShapeFile(path);
            lmMarkerMobiles.forEach(System.out::println);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

