package org.uy.base.dao;

import org.apache.ibatis.annotations.Param;
import org.uy.base.dto.BaseDto;

import java.util.List;
import java.util.Map;

/**
 * Dao层基础接口
 */
public interface BaseDao {

    <T extends BaseDto> List<T> find(@Param("params") Map<String,Object> params);

    <T extends  BaseDto> T findById(@Param("id") String id);

    int add(@Param("obj") BaseDto obj);

    int update(@Param("obj") BaseDto obj);

    int delete(@Param("id") String id);
}
