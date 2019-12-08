package com.gistone.service;

import com.gistone.VO.ResultVO;
import com.gistone.entity.LsRedlineinfo;
import com.gistone.entity.LsRedlineinfoProcess;
import com.gistone.entity.LsRedlineinfoTemplate;
import com.gistone.entity.LsRedlineinfoVersion;


public interface LsRedlineinfoService {

    ResultVO processInsert(LsRedlineinfoProcess lsRedlineinfoProcess)throws Exception;
    ResultVO processDelete(LsRedlineinfoProcess lsRedlineinfoProcess)throws Exception;
    ResultVO processUpdate(LsRedlineinfoProcess lsRedlineinfoProcess)throws Exception;


    ResultVO templateInsert(LsRedlineinfoTemplate lsRedlineinfoTemplate)throws Exception;
    ResultVO templateDelete(LsRedlineinfoTemplate lsRedlineinfoTemplate)throws Exception;
    ResultVO templateUpdate(LsRedlineinfoTemplate lsRedlineinfoTemplate)throws Exception;


    ResultVO versionInsert(LsRedlineinfoVersion lsRedlineinfoVersion)throws Exception;
    ResultVO versionDelete(LsRedlineinfoVersion lsRedlineinfoVersion)throws Exception;
    ResultVO versionUpdate(LsRedlineinfoVersion lsRedlineinfoVersion)throws Exception;


    ResultVO infoInsert(LsRedlineinfo lsRedlineinfo)throws Exception;
    ResultVO infoDelete(LsRedlineinfo lsRedlineinfo)throws Exception;
    ResultVO infoUpdate(LsRedlineinfo lsRedlineinfo)throws Exception;
//    ResultVO audit(LsRedlineinfo lsRedlineinfo)throws Exception;


    ResultVO export(String data1,String data2,String data3,String data4)throws Exception;


}
