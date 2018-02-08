package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatisticsDao{

    List<Map<String,Object>> watchTimeTrend(@Param("year")String year);

    List<Map<String,Object>> episodeTypeTop();

    List<Map<String,Object>> movieTypeTop();

    List<Map<String,Object>> animationTypeTop();

    Map<String,Object> totalCount();

    List<Map<String,Object>> episodePerformersTop();

    List<Map<String,Object>> moviePerformersTop();

    List<Map<String,Object>> animationPerformersTop();

    List<Map<String,Object>> episodeDramaTypePie();

    List<Map<String,Object>> movieRegionPie();

    List<Map<String,Object>> animationDramaTypePie();

    List<Map<String,Object>> levelRadar();
}
