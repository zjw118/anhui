package com.gistone.bjhky.mapper;

import com.gistone.bjhky.entity.SQLAdapter;

import java.util.List;
import java.util.Map;

/**
 * ClassName: GetBySqlMapper 
 * @param <E>
 * @Description: 通过原始SQL获取数据
 */
public interface GetBySqlMapper<E> {
    List<E> list(SQLAdapter sQLAdapter);
    
    Map map(SQLAdapter sQLAdapter);
    
    int total(SQLAdapter sQLAdapter);
    
    int insert(SQLAdapter sQLAdapter);
    
    int update(SQLAdapter sQLAdapter);
    
    int delete(SQLAdapter sQLAdapter);
    
    List<Map> findRecords(SQLAdapter sqlAdapter);
    
    int findrows(SQLAdapter sqlAdapter);
}