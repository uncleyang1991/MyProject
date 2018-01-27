package org.uy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.base.dao.BaseDao;
import org.uy.entity.EpisodeDto;

import java.util.List;
import java.util.Map;

@Repository()
public interface EpisodeDao extends BaseDao{

    List<EpisodeDto> findEpisodeBySearchItem(@Param("start")Integer start,@Param("length") Integer length,@Param("item") Map<String,Object> item);

    int getEpisodeCountBySearchItem(@Param("item") Map<String,Object> item);

    EpisodeDto findEpisodeById(@Param("id")String id);
}
