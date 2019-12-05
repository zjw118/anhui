package com.gistone.mapper;

import com.gistone.entity.LsRedlineinfo;
import com.gistone.entity.LsRedlineinfoProcess;
import com.gistone.entity.LsRedlineinfoTemplate;
import com.gistone.entity.LsRedlineinfoVersion;
import com.gistone.util.PageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LsRedlineinfoMapper {


    int processInsert(LsRedlineinfoProcess lsRedlineinfoProcess);
    int processDelete(LsRedlineinfoProcess lsRedlineinfoProcess);
    int processUpdate(LsRedlineinfoProcess lsRedlineinfoProcess);
    int getPoSumProcess(PageBean pageBean);
    List<Object> selectPoListProcess(PageBean pageBean);
    List<LsRedlineinfoProcess> LsRedlineinfoProcessList();


    int templateInsert(LsRedlineinfoTemplate lsRedlineinfoTemplate);
    int templateDelete(LsRedlineinfoTemplate lsRedlineinfoTemplate);
    int templateUpdate(LsRedlineinfoTemplate lsRedlineinfoTemplate);
    int getPoSumTemplate(PageBean pageBean);
    List<Object> selectPoListTemplate(PageBean pageBean);
    List<LsRedlineinfoTemplate> LsRedlineinfoTemplateList();


    int versionInsert(LsRedlineinfoVersion lsRedlineinfoVersion);
    int versionDelete(LsRedlineinfoVersion lsRedlineinfoVersion);
    int versionUpdate(LsRedlineinfoVersion lsRedlineinfoVersion);
    int getPoSumVersion(PageBean pageBean);
    List<Object> selectPoListVersion(PageBean pageBean);
    List<LsRedlineinfoVersion> LsRedlineinfoVersionList();


    int infoInsert(LsRedlineinfo lsRedlineinfo);
    int infoDelete(LsRedlineinfo lsRedlineinfo);
    int infoUpdate(LsRedlineinfo lsRedlineinfo);
    int getPoSumInfo(PageBean pageBean);
    List<Object> selectPoListInfo(PageBean pageBean);

    Integer getAudit(@Param(value="id") Integer id);


}
