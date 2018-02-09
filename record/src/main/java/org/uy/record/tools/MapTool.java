package org.uy.record.tools;

import org.uy.record.entity.AnimationDto;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.entity.MovieDto;

import java.util.Map;

public class MapTool {

    /**
     * map转换为EpisodeDto
     * @param params
     * @return
     */
    public static EpisodeDto mapToEpisode(Map<String, Object> params){
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

    /**
     * map转换为MovieDto
     * @param params
     * @return
     */
    public static MovieDto mapToMovie(Map<String, Object> params){
        MovieDto movie = new MovieDto();
        movie.setId(params.get("id").toString());
        movie.setName(params.get("name").toString());
        movie.setType(params.get("type").toString());
        movie.setDuration(Integer.parseInt(params.get("duration").toString()));
        movie.setRegion(params.get("region").toString());
        movie.setIntroduce(params.get("introduce").toString());
        movie.setLevel(Integer.parseInt(params.get("level").toString()));
        movie.setPerformers(params.get("performers").toString());
        movie.setDirector(params.get("director").toString());
        String showTime = params.get("showTime").toString();
        if(!"".equals(showTime)){
            movie.setShowTime(DateFormatTool.strToDate(null,showTime));
        }
        String watchTime = params.get("watchTime").toString();
        if(!"".equals(watchTime)){
            movie.setWatchTime(DateFormatTool.strToDate(null,watchTime));
        }
        return movie;
    }

    /**
     * map转换为AnimationDto
     * @param params
     * @return
     */
    public static AnimationDto mapToAnimation(Map<String, Object> params){
        AnimationDto animation = new AnimationDto();
        animation.setId(params.get("id").toString());
        animation.setName(params.get("name").toString());
        animation.setType(params.get("type").toString());
        animation.setTotal(Integer.parseInt(params.get("total").toString()));
        animation.setDramaType(params.get("dramaType").toString());
        animation.setIntroduce(params.get("introduce").toString());
        animation.setIsEnd(params.get("state").toString());
        animation.setLevel(Integer.parseInt(params.get("level").toString()));
        animation.setPerformers(params.get("performers").toString());
        animation.setWriter(params.get("writer").toString());
        animation.setWatchState(params.get("watchState").toString());
        String showTime = params.get("showTime").toString();
        if(!"".equals(showTime)){
            animation.setShowTime(DateFormatTool.strToDate(null,showTime));
        }
        String watchTime = params.get("watchTime").toString();
        if(!"".equals(watchTime)){
            animation.setWatchTime(DateFormatTool.strToDate(null,watchTime));
        }
        return animation;
    }
}
