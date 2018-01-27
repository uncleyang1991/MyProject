package org.uy.service;

import org.uy.entity.EpisodeDto;
import org.uy.page.DataTablesResult;

import java.util.Map;

public interface EpisodeService {

    DataTablesResult<EpisodeDto> findEpisodeBySearchItem(Map<String,Object> item);

    boolean addEpisode(Map<String,Object> params);

    EpisodeDto findEpisodeById(String id);

    boolean updateEpisode(Map<String,Object> params);

    EpisodeDto episodeInfoPull(String id) throws Exception;
}
