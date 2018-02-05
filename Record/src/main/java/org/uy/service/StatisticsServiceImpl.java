package org.uy.service;

import org.springframework.stereotype.Service;
import org.uy.dao.StatisticsDao;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Resource
    private StatisticsDao statisticsDao;

    @Override
    public List<Map<String, Object>> watchTimeTrend(String year) {
        if(year == null){
            year = String.valueOf(new Date().getYear()+1900);
        }
        return statisticsDao.watchTimeTrend(year);
    }

    @Override
    public List<Map<String, Object>> episodeTypeTop() {
        return statisticsDao.episodeTypeTop();
    }

    @Override
    public List<Map<String, Object>> movieTypeTop() {
        return statisticsDao.movieTypeTop();
    }

    @Override
    public List<Map<String, Object>> animationTypeTop() {
        return statisticsDao.animationTypeTop();
    }

    @Override
    public Map<String, Object> totalCount() {
        return statisticsDao.totalCount();
    }

    @Override
    public List<Map<String, Object>> episodePerformersTop() {
        return statisticsDao.episodePerformersTop();
    }

    @Override
    public List<Map<String, Object>> moviePerformersTop() {
        return statisticsDao.moviePerformersTop();
    }

    @Override
    public List<Map<String, Object>> animationPerformersTop() {
        return statisticsDao.animationPerformersTop();
    }

    @Override
    public List<Map<String, Object>> episodeDramaTypePie() {
        return statisticsDao.episodeDramaTypePie();
    }

    @Override
    public List<Map<String, Object>> movieRegionPie() {
        return statisticsDao.movieRegionPie();
    }

    @Override
    public List<Map<String, Object>> animationDramaTypePie() {
        return statisticsDao.animationDramaTypePie();
    }

    @Override
    public List<Map<String, Object>> levelRadar() {
        return statisticsDao.levelRadar();
    }

    public void setStatisticsDao(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }
}
