package com.gistone.entity;

import lombok.Data;
import java.util.Date;

@Data
public class LsRedlineinfoVersion {

    private Integer id;
    private Integer process_id;
    private Integer template_id;
    private String name;
    private Date updatetime;
    private Integer closed;


}
