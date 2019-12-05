package com.gistone.entity;


import lombok.Data;
import java.util.Date;

@Data
public class LsRedlineinfo {

    private Integer  id;
    private Integer  version_id;
    private String  ftp_shp;
    private String  name;
    private Date updatetime;
    private Integer  audit;
    private Integer  closed;


}
