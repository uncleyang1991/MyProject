package org.uy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.base.dao.BaseDao;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.entity.EpisodeDto;

import java.util.List;
import java.util.Map;

@Repository()
public interface EpisodeDao extends BaseDao{

    PageResult<EpisodeDto> findEpisodeBySearchItem(@Param("item") Map<String, Object> item,@Param("pageParameter")PageParameter parameter);

    EpisodeDto findEpisodeById(@Param("id")String id);
}
