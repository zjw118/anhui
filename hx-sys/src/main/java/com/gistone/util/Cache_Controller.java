package com.gistone.util;

/*
import com.gistone.geotools.util.Result;
import com.gistone.geotools.util.Status;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import java.io.BufferedReader;
import java.io.File;
import java.io.Serializable;
import java.io.FileReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
* @author  作者 李永亮
* @date 创建时间：2019年9月23日
* @version 1.1
* @parameter  
*//*

@RestController
@RequestMapping("cache/")
public class Cache_Controller {

	@RequestMapping("getSHP")
	public Object getSHP(@RequestBody Map<String,Object> requestData,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> dataMap= (Map)requestData.get("data");
		Result result = new Result();

		String layer = "";

		if(dataMap.get("layer")!=null && !"".equals(dataMap.get("layer").toString())){
			layer = dataMap.get("layer").toString().trim();
		}else{
			result.setMsg("shp文件参数未传递");
			result.setData("");
			result.setStatus(Status.ISNULL);
			return result;
		}

		//存储坐标数组
		JSONArray array = new JSONArray();

		try{
			//定义shp文件的绝对路径
			String shpPath = "E:\\3857\\"+layer+".shp";
			shpPath = "E:\\3857\\ld.shp";
			File file = new File (shpPath);

			FeatureJSON fjson = new FeatureJSON();
			StringBuffer fsb = new StringBuffer();
			fsb.append("{\"type\": \"FeatureCollection\",\"features\": ");

			//读取shp文件内容
			Map<String, Serializable> params = new HashMap<>();
			params.put("url", file.toURI().toURL());
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
			Charset charset = Charset.forName("GBK");
			newDataStore.setCharset(charset);
			String typeName = newDataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
			SimpleFeatureCollection collection = featureSource.getFeatures();
			SimpleFeatureIterator features = collection.features();

			while (features.hasNext()) {
				SimpleFeature feature = features.next();
				StringWriter writer = new StringWriter();
				fjson.writeFeature(feature, writer);
				JSONObject json = new JSONObject(writer.toString());
				array.put(json);
			}
			features.close();
			fsb.append(array.toString());
			fsb.append("}");
			System.out.println(array.toString());

			result.setMsg("ok");
			result.setData(fsb);
			result.setStatus(Status.SUCCESS);
		}catch(Exception e){
			System.out.println("读取SHP文件异常");
			System.out.println(e.getMessage());
			result.setStatus(Status.SYSTEM_EXCEPTION);
			result.setMsg("系统异常");
		}

		//对文件来源的空间数据进行处理
//		if(handleSHPData(array).equals("0")){
//			System.out.println("数据处理成功");
//		}

		String cc = "[{\n" +
				"    \"geometry\": {\n" +
				"      \"spatialReference\": {\n" +
				"        \"latestWkid\": 3857,\n" +
				"        \"wkid\": 102100\n" +
				"      },\n" +
				"      \"rings\": [\n" +
				"        [\n" +
				"          [12935939.423271682, 4886921.041715896],\n" +
				"          [12935693.39158689, 4887322.336114393],\n" +
				"          [12936128.12718526, 4887728.4078271575],\n" +
				"          [12936722.902811605, 4887336.668057196],\n" +
				"          [12936560.4741265, 4886961.648887172],\n" +
				"          [12935939.423271682, 4886921.041715896]\n" +
				"        ],\n" +
				"        [\n" +
				"          [12936073.188071182, 4887159.907429286],\n" +
				"          [12936366.992898652, 4887186.18265776],\n" +
				"          [12936331.163041644, 4887386.8298570085],\n" +
				"          [12936042.135528442, 4887360.554628535],\n" +
				"          [12936073.188071182, 4887159.907429286]\n" +
				"        ]\n" +
				"      ]\n" +
				"    },\n" +
				"    \"symbol\": null,\n" +
				"    \"attributes\": {\n" +
				"      \"ObjectID\": 4,\n" +
				"      \"name\": \"空心\",\n" +
				"      \"area\": null,\n" +
				"      \"type\": null,\n" +
				"      \"peopleNum\": null\n" +
				"    },\n" +
				"    \"popupTemplate\": null\n" +
				"  }, {\n" +
				"    \"geometry\": {\n" +
				"      \"spatialReference\": {\n" +
				"        \"latestWkid\": 3857,\n" +
				"        \"wkid\": 102100\n" +
				"      },\n" +
				"      \"rings\": [\n" +
				"        [\n" +
				"          [12936691.850268865, 4887167.073400688],\n" +
				"          [12936261.89198476, 4887226.789829036],\n" +
				"          [12936405.211412795, 4887644.80482747],\n" +
				"          [12936813.671782695, 4887628.084227533],\n" +
				"          [12936691.850268865, 4887167.073400688]\n" +
				"        ],\n" +
				"        [\n" +
				"          [12936142.459128065, 4887207.680571965],\n" +
				"          [12935827.156386388, 4887281.728943116],\n" +
				"          [12935882.09550047, 4887718.853198622],\n" +
				"          [12936281.001241831, 4887671.080055944],\n" +
				"          [12936142.459128065, 4887207.680571965]\n" +
				"        ]\n" +
				"      ]\n" +
				"    },\n" +
				"    \"symbol\": null,\n" +
				"    \"attributes\": {\n" +
				"      \"ObjectID\": 5,\n" +
				"      \"name\": \"分离\",\n" +
				"      \"area\": null,\n" +
				"      \"type\": null,\n" +
				"      \"peopleNum\": null\n" +
				"    },\n" +
				"    \"popupTemplate\": null\n" +
				"  }]\n";
		JSONArray c1 = new JSONArray(cc);
		//对前端返回的的空间数据进行处理
		if(handleWebData(c1).equals("0")){
			System.out.println("数据处理成功");
		}
		return result;
	}

	private static String[] spaceCheck(File newFile){

//		int p = 0;
//		for (int j = 0; j <coords.size() ; j++) {
//			Geometry g1 = new GeometryFactory().createPolygon(coords.get(j));
//			for (int k = 0; k <coords.size() ; k++) {
//				if(j != k){
//					System.out.println(j+"----"+k);
//					//不能自己和自己判断
//					Geometry g2 = new GeometryFactory().createPolygon(coords.get(k));
//					boolean isIter = g1.intersects(g2);
//					if(isIter){
//						System.out.println("相交");
//						p++;
//					}else{
//						System.out.println("不相交");
//					}
//
//					if(g1.within( g2 )){
//						System.out.println("g1包含g2");
//					}else{
//						System.out.println("g1不包含g2");
//					}
//
//				}
//
//			}
//		}

//					if(p > 0){
//						//有相交的存在，异常返回
//						System.out.println("相交ID："+jobj.getJSONObject("attributes").getInt("ObjectID"));
//						for (int j = 0; j <coords.size() ; j++) {
//							//构造具有给定外部边界的多边形
//							Polygon polygon = geometryFactory.createPolygon(coords.get(j));
//							featureBuilder.add(polygon);
//						}
//					}else{
//						for (int j = 0; j <coords.size() ; j++) {
//							//构造具有给定外部边界的多边形
//							Polygon polygon = geometryFactory.createPolygon(coords.get(j));
//							featureBuilder.add(polygon);
//						}
//
//					}

		return new String[1];
	}

	*/
/**
	 * 前端页面来源的json数据处理
	 * @param array：JSONArray格式数据
	 * @return
	 *//*

	private static String handleWebData(JSONArray array){
		try{
			final SimpleFeatureType TYPE = createFeatureType();
			//在我们创建功能时收集功能的列表
			List<SimpleFeature> features = new ArrayList<>();
			//geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

			for(int i = 0;i< array.length();i++){
				SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
				JSONObject jobj = (JSONObject) array.get(i);
				JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("rings");
				if(zhj.length()>1){
					//大于1说明在一条记录中有两个面，需要进行判断
					//根据面的数量先创建数组，用于最终比较
					List<Coordinate[]> coords = new ArrayList<>();
					for (int j = 0; j < zhj.length(); j++) {
						Coordinate[] coor = new Coordinate[zhj.getJSONArray(j).length()];
						for (int k = 0; k <zhj.getJSONArray(j).length() ; k++) {
							coor[k] = new Coordinate(zhj.getJSONArray(j).getJSONArray(k).getDouble(0), zhj.getJSONArray(j).getJSONArray(k).getDouble(1));
						}
						coords.add(coor);
					}
					Polygon[] polygon = new Polygon[coords.size()];
					for (int j = 0; j <coords.size() ; j++) {
						polygon[j] = geometryFactory.createPolygon(coords.get(j));
					}
					MultiPolygon mp = geometryFactory.createMultiPolygon(polygon);
					featureBuilder.add(mp);
				}else{
					//根据实际坐标的多少创建数组
					Coordinate[] coords  = new Coordinate[zhj.getJSONArray(0).length()];
					//跳一级循环，直接获取最终的坐标数组
					for (int k = 0; k <zhj.getJSONArray(0).length() ; k++) {
						coords[k] = new Coordinate(zhj.getJSONArray(0).getJSONArray(k).getDouble(0), zhj.getJSONArray(0).getJSONArray(k).getDouble(1));
					}
					//构造具有给定外部边界的多边形
					Polygon polygon = geometryFactory.createPolygon(coords);
					featureBuilder.add(polygon);
				}

				//这里按顺序添加属性
				featureBuilder.add(jobj.getJSONObject("attributes").getInt("ObjectID"));
				featureBuilder.add(jobj.getJSONObject("attributes").getString("name"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("area"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("type"));
//				featureBuilder.add(jobj.getJSONObject("attributes").getString("peopleNum"));
				SimpleFeature feature = featureBuilder.buildFeature(null);
				features.add(feature);
			}


			//创建shp文件并写入数据
			if(createSHP(TYPE, features, new File("E:\\3857\\111111.shp")).equals("0")){
				System.out.println("文件保存成功");
			}

		}catch(Exception e){
			System.out.println("web传入来源数据处理错误");
			System.out.println(e.getMessage());
			return "1";
		}
		return "0";
	}

	*/
/**
	 * 对从shp文件读取到的数据进行处理
	 * @param array：空间几何图形
	 * @return
	 *//*

	private static String handleSHPData(JSONArray array){
		try{
			System.out.println(array.toString());

			final SimpleFeatureType TYPE = createFeatureType();
			//在我们创建功能时收集功能的列表
			List<SimpleFeature> features = new ArrayList<>();
			//geometryFactory将用于创建每个要素的几何体属性，使用多边形对象作为位置。
			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
			SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

			for(int i = 1;i< array.length();i++){
				JSONObject jobj = (JSONObject) array.get(i);
				JSONArray zhj = jobj.getJSONObject("geometry").getJSONArray("coordinates");
				JSONArray lin = new JSONArray();
				for (int j = 0; j < zhj.length(); j++) {
					for (int k = 0; k <zhj.getJSONArray(j).length() ; k++) {
						for (int l = 0; l <zhj.getJSONArray(j).getJSONArray(k).length() ; l++) {
							lin.put(zhj.getJSONArray(j).getJSONArray(k).getJSONArray(l));
						}
					}
				}
				System.out.println(lin.length());

				Coordinate[] coords  = new Coordinate[lin.length()];
				for (int j = 0; j <lin.length() ; j++) {
					coords[j] = new Coordinate(lin.getJSONArray(j).getDouble(0), lin.getJSONArray(j).getDouble(1));
				}

				Polygon polygon = geometryFactory.createPolygon(coords);
				featureBuilder.add(polygon);
				SimpleFeature feature = featureBuilder.buildFeature(null);
				features.add(feature);
			}

			//创建shp文件并写入数据
			if(createSHP(TYPE, features, new File("E:\\3857\\111111.shp")).equals("0")){
				System.out.println("文件保存成功");
			}

		}catch(Exception e){
			System.out.println("文件来源数据处理错误");
			System.out.println(e.getMessage());
			return "1";
		}
		return "0";
	}

	*/
/**
	 * 创建shp文件并写入数据
	 * @param TYPE： 空间数据模板
	 * @param features：数据内容
	 * @return
	 *//*

	private static String createSHP(SimpleFeatureType TYPE, List<SimpleFeature> features, File newFile){
		try{
			//从FeatureCollection创建shapefile
			//DataStoreFactory与参数一起使用表示我们想要空间索引
			//使用createSchema(SimpleFeatureType)方法来设置shapefile
			ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
			Map<String, Serializable> params = new HashMap<>();
			params.put("url", newFile.toURI().toURL());
			params.put("create spatial index", Boolean.TRUE);

			ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
			//设置字符编码
			Charset charset = Charset.forName("GBK");
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
				System.exit(0); // success!
			} else {
				System.out.println(typeName + " 不支持读/写访问");
				System.exit(1);
			}

		}catch(Exception e){
			System.out.println("文件生成报错");
			System.out.println(e.getMessage());
			return "1";
		}
		return "0";
	}

	*/
/**
	 * 定义空间数据模板
	 * @return
	 *//*

	private static SimpleFeatureType createFeatureType() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("Location");
		builder.setCRS(DefaultGeographicCRS.WGS84); // 坐标参考系
		// 按顺序添加属性
		builder.add("the_geom", MultiPolygon.class);
		builder.add("ObjectID", Integer.class);
		builder.length(100).add("name", String.class);// 字段的宽度为100个字符
		builder.length(100).add("area", String.class);
		builder.length(100).add("type", String.class);
		builder.length(100).add("peopleNum", String.class);

		// 生成类型
		final SimpleFeatureType LOCATION = builder.buildFeatureType();
		return LOCATION;
	}

}
*/
