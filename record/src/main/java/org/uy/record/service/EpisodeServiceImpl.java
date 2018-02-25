package org.uy.record.service;

import org.springframework.stereotype.Service;
import org.uy.record.dao.EpisodeDao;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.module.EpisodeInfoPull;
import org.uy.record.page.DataTablesResult;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;
import org.uy.record.tools.IdTool;
import org.uy.record.tools.MapTool;

import javax.annotation.Resource;
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
        PageParameter parameter = new PageParameter();
        parameter.setPageSize(Integer.parseInt(item.get("length").toString()));
        parameter.setPage(Integer.parseInt(item.get("start").toString())/Integer.parseInt(item.get("length").toString())+1);
        PageResult<EpisodeDto> pageResult = episodeDao.findEpisodeBySearchItem(item,parameter);
        dataTablesResult.setRecordsTotal(pageResult.getTotalRow());
        dataTablesResult.setRecordsFiltered(pageResult.getTotalRow());
        dataTablesResult.setData(pageResult);
        return dataTablesResult;
    }

    @Override
    public boolean addEpisode(Map<String,Object> params) {
        params.put("id", IdTool.getUUID());
        EpisodeDto episode = MapTool.mapToEpisode(params);
        int result = episodeDao.add(episode);
        return result == 1;
    }

    @Override
    public EpisodeDto findEpisodeById(String id) {
        return episodeDao.findEpisodeById(id);
    }

    @Override
    public boolean updateEpisode(Map<String, Object> params) {
        EpisodeDto episode = MapTool.mapToEpisode(params);
        int result = episodeDao.update(episode);
        return result == 1;
    }

    @Override
    public EpisodeDto episodeInfoPull(String id){
        return eip.getEpisodeInfo(id);
    }

    public void setEpisodeDao(EpisodeDao episodeDao) {
        this.episodeDao = episodeDao;
    }

    public void setEip(EpisodeInfoPull eip) {
        this.eip = eip;
    }

}
