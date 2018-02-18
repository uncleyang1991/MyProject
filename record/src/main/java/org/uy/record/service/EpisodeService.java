package org.uy.record.service;
;

import org.uy.record.entity.EpisodeDto;
import org.uy.record.page.DataTablesResult;

import java.util.Map;

public interface EpisodeService {

    DataTablesResult<EpisodeDto> findEpisodeBySearchItem(Map<String,Object> item);

    boolean addEpisode(Map<String,Object> params);

    EpisodeDto findEpisodeById(String id);

    boolean updateEpisode(Map<String,Object> params);

    EpisodeDto episodeInfoPull(String id);
}
