package org.uy.service;

import org.springframework.stereotype.Service;
import org.uy.dao.EpisodeDao;
import org.uy.entity.EpisodeDto;
import org.uy.module.EpisodeInfoPull;
import org.uy.page.DataTablesResult;
import org.uy.tools.DateFormatTool;
import org.uy.tools.IdTool;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Resource()
    private EpisodeDao episodeDao;

    @Resource
    private EpisodeInfoPull eip;

    @Override
    public DataTablesResult<EpisodeDto> findEpisodeBySearchItem(Map<String, Object> item) {
        DataTablesResult<EpisodeDto> dataTablesResult = new DataTablesResult<EpisodeDto>();
        int count = episodeDao.getEpisodeCountBySearchItem(item);
        dataTablesResult.setRecordsTotal(count);
        dataTablesResult.setRecordsFiltered(count);
        List<EpisodeDto> data = episodeDao.findEpisodeBySearchItem(Integer.valueOf(item.get("start").toString()),Integer.valueOf(item.get("length").toString()),item);
        dataTablesResult.setData(data);
        return dataTablesResult;
    }

    @Override
    public boolean addEpisode(Map<String,Object> params) {
        params.put("id", IdTool.getUUID());
        EpisodeDto episode = mapToEpisode(params);
        int result = episodeDao.add(episode);
        return result == 1;
    }

    @Override
    public EpisodeDto findEpisodeById(String id) {
        return episodeDao.findEpisodeById(id);
    }

    @Override
    public boolean updateEpisode(Map<String, Object> params) {
        EpisodeDto episode = mapToEpisode(params);
        int result = episodeDao.update(episode);
        return result == 1;
    }

    @Override
    public EpisodeDto episodeInfoPull(String id) throws Exception{
        return eip.getEpisodeInfo(id);
    }

    /**
     * map转换为EpisodeDto
     * @param params
     * @return
     */
    private EpisodeDto mapToEpisode(Map<String, Object> params){
        EpisodeDto episode = new EpisodeDto();
        episode.setId(params.get("id").toString());
        episode.setName(params.get("name").toString());
        episode.setType(params.get("type").toString());
        episode.setTotal(Integer.parseInt(params.get("total").toString()));
        episode.setDramaType(params.get("dramaType").toString());
        episode.setIntroduce(params.get("introduce").toString());
        episode.setIsEnd(params.get("state").toString());
        episode.setLevel(Integer.parseInt(params.get("level").toString()));
        episode.setPerformers(params.get("performers").toString());
        episode.setWriter(params.get("writer").toString());
        episode.setWatchState(params.get("watchState").toString());
        String showTime = params.get("showTime").toString();
        if(!"".equals(showTime)){
            episode.setShowTime(DateFormatTool.strToDate(null,showTime));
        }
        String watchTime = params.get("watchTime").toString();
        if(!"".equals(watchTime)){
            episode.setWatchTime(DateFormatTool.strToDate(null,watchTime));
        }
        return episode;
    }

    public void setEpisodeDao(EpisodeDao episodeDao) {
        this.episodeDao = episodeDao;
    }

}
