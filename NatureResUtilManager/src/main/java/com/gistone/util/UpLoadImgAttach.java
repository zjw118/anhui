package com.gistone.util;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping
public class UpLoadImgAttach {

	@Autowired
	private ConfigUtil configUtil;
	@Autowired
	private UrlsUtil urlUtil;
	
	/**
	 * 文件上传返回路径
	 * 
	 * @return
	 * 	name：文件原始名称
	 * 	absPath：绝对路径
	 * 	RelPath：相对路径
	 */
	@RequestMapping("upload/up_load")
	public Result up_load(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ClientUtil.SetCharsetAndHeader(request, response);
			response.setContentType("text/html; charset=UTF-8");
//			MultipartConfigFactory factory = new MultipartConfigFactory();

			//1.获取参数：文件夹名称
			String fileName = request.getParameter("fileName").trim();
			if(!RegUtil.CheckParameter(fileName, null, null, false)){
				LogUtil.getLogger().error("up_load 没有fileName参数");
				return Result.build(1003,  "up_load 没有fileName参数");
			}
			
			//判断文件参数
			if (!ServletFileUpload.isMultipartContent(request)) {
				LogUtil.getLogger().error("无文件数据");
				return Result.build(1003, "无文件数据");
			}
			
			// 2.获取文件夹的绝对路径与创建文件夹
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date date = new Date();
			String dat = sdf.format(date);
			String savePath = urlUtil.geturl() + "upload/" + fileName+"/"+dat+"/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			savePath = urlUtil.geturl() + "/";
			
			// 3.迭代文件
			//接口返回数据集合
			List<Map> maplist=new ArrayList<Map>();
			
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> items = req.getFileMap();
			Iterator<MultipartFile> itr = items.values().iterator();
			while (itr.hasNext()) {
				//接口返回数据集合
				Map<String,String> map=new HashMap<String, String>();
				MultipartFile item = itr.next();

				// 文件原始名称
				String oldfileName = item.getOriginalFilename();//getName方法用于获得文件上传字段中的文件名
				// 文件后缀名
				String extensionName = oldfileName.substring(oldfileName.lastIndexOf(".")+1);
				// 文件新名称;
				String uuidStr = UuidUtil.get32UUID();
				String newFileName =  uuidStr + "_" +oldfileName.replaceAll("\\s*", "");
				// 相对路径
				String adjunctPath = "upload/" + fileName+"/"+dat+"/"+newFileName;
				
				//上传文件
				try {
					File uploadedFile = new File(savePath + adjunctPath);
					item.transferTo(uploadedFile);
				} catch (Exception e) {
					LogUtil.getLogger().error("上传文件失败。", e);
					return Result.build(1003, "上传文件失败。");
				}
				
				//接口返回信息
				map.put("absPath", savePath + adjunctPath);
				map.put("name", oldfileName);
				map.put("RelPath", adjunctPath);
				
				//arcgis压缩文件上传
				String shpName = null;
				if("shapFile".equals(fileName)){
					 if("rar".equals(extensionName.toLowerCase())){
						 shpName = UnRARUtil.unRarArcGISFiles(savePath + adjunctPath, configUtil.getShapeFileUrl(), uuidStr);
					 }else{
						 shpName = ZipUtil.unZipArcGISFiles(savePath + adjunctPath, configUtil.getShapeFileUrl(), uuidStr);
					 }
					 map.put("RelPath", shpName);
				}
				
				maplist.add(map);
			}
			
			return Result.build(null, null,maplist);
		} catch (Exception e1) {
			LogUtil.getLogger().error("上传文件异常。", e1);
			return Result.build(1001, "上传文件异常。");
		}

	}
	
	
	
	/**
	 * 删除文件操作
	 * 
	 * @param requestDate
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("file/del_file")
	public Result del_file(@RequestBody Map<String, Object> requestDate,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ClientUtil.SetCharsetAndHeader(request, response);
			Map<String, Object> data = (Map) requestDate.get("data");
			if (!RegUtil.CheckParameter(data.get("RelPath"), null, null,
					false)) {
				LogUtil.getLogger().error("del_file中的RelPath为空");
				return Result.build(1003, "del_file中的RelPath为空");
			}
			// 获取附件id
			Integer RelPath = Integer.valueOf(data.get("RelPath")
					.toString());
			String replace = urlUtil.geturl().replace("upload/", "");
			String path =replace +RelPath;
			// 删除文件
			try {
				File file = new File(path);
				file.delete();
			} catch (Exception e) {
				LogUtil.getLogger().error("del_file 删除操作异常", e);
				return Result.build(1001, "del_file 删除操作异常");
			}

				return Result.ok();
			
		
		} catch (Exception e) {
			LogUtil.getLogger().error("del_file 删除文件异常", e);
			return Result.build(1001, "del_file 删除文件异常");
		}
	}
	
	/*
	@RequestMapping("upload/kind_upload")
	public JSONObject kind_upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject js = new JSONObject();
		try {
			ClientUtil.SetCharsetAndHeader(request, response);
			MultipartConfigFactory factory = new MultipartConfigFactory();
			String fileName = request.getParameter("fileName").trim();// 文件夹名称
			
			
			
			if(!RegUtil.CheckParameter(fileName, null, null, false)){
				LogUtil.getLogger().error("up_load 没有fileName参数");
				js.put("msg", 1);
				return js;
			}
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> items = req.getFileMap();
			

			// 添加时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date date = new Date();
			String dat = sdf.format(date);
			// 文件保存目录路径
			String savePath = urlUtil.geturl()+ fileName+"/"+dat+"/";
			// String path="upload\\" + userId+ "\\";
			// 最大文件大小
			long maxSize = 1000000;
			response.setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(request)) {
				LogUtil.getLogger().error("无文件数据");
				js.put("msg", 2);
				return js;
			}

			// 检查目录
			// 创建文件夹
			// path+=targetId;
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			List<Map> maplist=new ArrayList<Map>();
			// 迭代文件
			Iterator<MultipartFile> itr = items.values().iterator();
			while (itr.hasNext()) {
				Map<String,String> map=new HashMap<String, String>();
	
				MultipartFile item = itr.next();

			


				String thefileName = item.getOriginalFilename();// getName方法用于获得文件上传字段中的文件名
				// 检查文件大小
				String d=thefileName.substring(thefileName.lastIndexOf(".")+1);
			
				// 文件名
				long newFileName =new Date().getTime();
				// 相对路径
				String adjunctPath = "upload/" + fileName+"/"+dat+"/"+newFileName + "." + d;
				try {
					File uploadedFile = new File(savePath, newFileName +"."+d);
					item.transferTo(uploadedFile);
				} catch (Exception e) {
					LogUtil.getLogger().error("上传文件失败。", e);
					js.put("msg", 3);
					return js;
				}
				String shpName = null;
				if("shapFile".equals(fileName)){
					 if("rar".equals(d.toLowerCase())){
						 shpName = UnRARUtil.unRarArcGISFiles(savePath + newFileName +"."+d , "D://shp",newFileName);
					 }else{
						 shpName = ZipUtil.unZipArcGISFiles(savePath + newFileName +"."+d , "D://shp",newFileName);
					 }
					 map.put("RelPath", shpName);
				}else{
					map.put("RelPath", adjunctPath);
				}
				js.put("url", adjunctPath);
				map.put("absPath", savePath+newFileName + "." + d);
				map.put("name", thefileName);
				long size = item.getSize();// 文件大小
				maplist.add(map);
			}
			
			return js;
		} catch (Exception e1) {
			LogUtil.getLogger().error("上传文件异常。", e1);
			js.put("msg", 4);
			return js;
		}

	}*/
	
	
}
