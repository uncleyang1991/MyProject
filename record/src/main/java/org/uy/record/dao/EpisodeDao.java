package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

import java.util.Map;

@Repository()
public interface EpisodeDao extends BaseDao{

    PageResult<EpisodeDto> findEpisodeBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter")PageParameter parameter);

    EpisodeDto findEpisodeById(@Param("id")String id);
}
