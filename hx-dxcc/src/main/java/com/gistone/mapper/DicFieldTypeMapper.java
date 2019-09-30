package com.gistone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gistone.entity.DicFieldType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2019-03-01
 */
public interface DicFieldTypeMapper extends BaseMapper<DicFieldType> {

	int saveFiledValueBatch(List<DicFieldType> list);

	int updateFiledTypeById(@Param("pkId") String pkId);

	List<Map<String,Object>> selectFiledMap(@Param("id") Integer id);

	Integer selectFiledTypeById(@Param("id") Integer id);

	String selectSingleValue(@Param("id") Integer id);

	String selectRadioValue (@Param("id") Integer id);

	List<String> getMutilValue(@Param("ids") List<Integer> ids);

}
