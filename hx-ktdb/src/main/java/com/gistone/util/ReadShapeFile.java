package com.gistone.util;


/**
 * @author zf1017@foxmail.com
 * @date 2019/4/30 0030 14:28
 * @description
 */
public class ReadShapeFile {

    /*public ArrayList<LmMarkerMobile> readShapeFile(String filePath) {
        ArrayList<LmMarkerMobile> modelList = new ArrayList<>();
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
        } else {
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
                    ArrayList<LmMarkerMobile> models = getShapeFile(file);
                    modelList.addAll(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelList;
    }

    private ArrayList<LmMarkerMobile> getShapeFile(File file) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", file.toURI().toURL());
        ArrayList<LmMarkerMobile> models = new ArrayList<>();
        DataStore dataStore = DataStoreFinder.getDataStore(map);
        //字符转码，防止中文乱码
        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            LmMarkerMobile model = new LmMarkerMobile();
            Iterator<? extends Property> iterator = feature.getValue().iterator();
            String type = "";
            String target = "";
            String code = "";
            String hxcode = "";
            while (iterator.hasNext()) {
                Property property = iterator.next();
//                System.out.println(property);
                //property数据与实体类对应

//                if(property.getName().toString().equals("type")){
//                    type = property.getValue().toString();
//                }
                if (property.getName().toString().equals("jzcode")) {
                    model.setJzNumber(property.getValue().toString());
                }
//                if(property.getName().toString().equals("attribute")){
//                    target = property.getValue().toString();
//                }
                if (property.getName().toString().equals("pac")) {
                    model.setCode(property.getValue().toString());
//                    code = property.getValue().toString();
                }
                if (property.getName().toString().equals("longitude")) {
                    model.setLongitude(Double.parseDouble(property.getValue().toString()));
                }
                if (property.getName().toString().equals("latitude")) {
                    model.setLatitude(Double.parseDouble(property.getValue().toString()));
                }
                if (property.getName().toString().equals("redline_id")) {
                    model.setRedlineId(Integer.parseInt(property.getValue().toString()));
                }
//                model.setJzNumber(code+"-"+type+target+"-"+hxcode);


            }
            models.add(model);
        }
        return models;
    }

    public String readShapeFileToStr(String filePath) {
        String result = "";
        try {
            FeatureJSON fjson = new FeatureJSON();
            StringBuffer fsb = new StringBuffer();
            JSONArray array = new JSONArray();
            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

            File file = new File(filePath);
            fsb.append("{\"type\": \"FeatureCollection\",\"features\": ");
            ShapefileDataStore shpDataStore = null;

            shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            //设置字符编码
            Charset charset = Charset.forName("GBK");
            shpDataStore.setCharset(charset);
            //获取图层名称
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection sfc = featureSource.getFeatures();
            SimpleFeatureIterator itertor = sfc.features();
            while (itertor.hasNext()) {
                SimpleFeature feature = itertor.next();
                //  feature.getAttribute(name)

                //contains

                StringWriter writer = new StringWriter();
                fjson.writeFeature(feature, writer);
                JSONObject json = new JSONObject(writer.toString());
                array.put(json);

		    Map<String,Object> data  = new HashMap<String, Object>();
			    Collection<Property> p = feature.getProperties();
			    Iterator<Property> it = p.iterator();
			    while(it.hasNext()) {
				Property pro = it.next();
				String field = pro.getName().toString();
				String value = pro.getValue().toString();
				field = field.equals("the_geom")?"wkt":field;
				data.put(field, value);
			    }
			    list.add(data);
            }

            System.out.println(array.toString());
            result = array.toString();

            itertor.close();
            fsb.append(array.toString());
            fsb.append("}");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


        return result;

    }

    public static void main(String[] args) {
        try {
            String path = "D:/Work/gistone/文档/祁连山生态红线/祁连山山水林田湖生态修复试点/Export_Output_3.shp";
            ReadShapeFile readShapeFile = new ReadShapeFile();
            *//*String s = readShapeFile.readShapeFileToStr(path);
            System.out.println(s);*//*
//            ArrayList<LmMarkerMobile> lmMarkerMobiles = readShapeFile.readShapeFile(path);
//            lmMarkerMobiles.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
}
