package org.uy.record.module;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.uy.record.entity.MovieDto;
import org.uy.record.system.SystemCount;
import org.uy.record.tools.DateFormatTool;

/**
 * 从豆瓣网上拉取电影信息
 */

@Component
public class MovieInfoPull {

    private final Logger log = Logger.getLogger(MovieInfoPull.class);

    private String BASE_URL = "https://movie.douban.com/subject/";

    public MovieDto getMovieInfo(String id){
        MovieDto dto = new MovieDto();

        try {
            //开始抓取
            Document document = Jsoup.connect(BASE_URL+id).post();
            Document celDoc = Jsoup.connect(BASE_URL+id+"/celebrities").post();

            //片名
            String name = celDoc.title();
            name = name.substring(0,name.lastIndexOf(" "));
            dto.setName(name.trim());

            Elements ps = celDoc.select(".movie-summary>.attrs").get(0).children();
            for(Element p:ps){
                if(p.children().size() < 1){
                    continue;
                }
                //导演
                if("导演:".equals(p.child(0).text())){
                    String directors = p.text();
                    directors = directors.substring(directors.indexOf("导演:")+3);
                    StringBuilder director = new StringBuilder();
                    for(String d:directors.split("/")){
                        director.append(d.trim()+",");
                    }
                    dto.setDirector(director.substring(0,director.length()-1));
                    continue;
                }
                //剧情类型
                if("类型:".equals(p.child(0).text())){
                    String types = p.text();
                    types = types.substring(types.indexOf("类型:")+3);
                    StringBuilder type = new StringBuilder();
                    for(String d:types.split(",")){
                        type.append(d.trim()+",");
                    }
                    dto.setType(type.substring(0,type.length()-1));
                    continue;
                }
                //地区
                if("地区:".equals(p.child(0).text())){
                    String region = p.text();
                    region = region.substring(region.indexOf("地区:")+3);
                    String[] regions = region.split(",");
                    if(regions.length > 1){
                        region = regions[0];
                    }
                    if("中国大陆".equals(region.trim())){
                        region = "中国";
                    }
                    dto.setRegion(region);
                    continue;
                }
                //演员
                if("主演:".equals(p.child(0).text())){
                    String performers = p.text();
                    performers = performers.substring(performers.indexOf("主演:")+3);
                    StringBuilder performer = new StringBuilder();
                    for(String d:performers.split("/")){
                        performer.append(d.trim()+",");
                    }
                    dto.setPerformers(performer.substring(0,performer.length()-1));
                    continue;
                }
                //片长
                if("片长:".equals(p.child(0).text())){
                    String duration = p.text();
                    duration = duration.substring(duration.indexOf("片长:")+3,duration.indexOf("分钟"));
                    dto.setDuration(Integer.parseInt(duration.trim()));
                    continue;
                }
                //上映时间
                if("上映:".equals(p.child(0).text())){
                    String showTimes = p.text();
                    showTimes = showTimes.substring(showTimes.indexOf("上映:")+3).trim();
                    if(showTimes.contains("(中国大陆)")){
                        String showTime = showTimes.substring(showTimes.indexOf("(中国大陆)")-10,showTimes.indexOf("(中国大陆)"));
                        dto.setShowTime(DateFormatTool.strToDate(null,showTime));
                        continue;
                    }
                    if(showTimes.contains("("+dto.getRegion()+")")){
                        String showTime = showTimes.substring(showTimes.indexOf("("+dto.getRegion()+")")-10,showTimes.indexOf("("+dto.getRegion()+")"));
                        dto.setShowTime(DateFormatTool.strToDate(null,showTime));
                        continue;
                    }

                    dto.setShowTime(DateFormatTool.strToDate(null,showTimes.substring(0,10)));
                }
            }

            //内容简介
            String introduce = document.getElementById("link-report").text().trim();
            if(introduce.lastIndexOf('@') != -1){
                introduce = introduce.substring(0,introduce.lastIndexOf('@'));
            }
            if(introduce.lastIndexOf('©') != -1){
                introduce = introduce.substring(0,introduce.lastIndexOf('©'));
            }
            dto.setIntroduce(introduce);
        } catch (Exception e) {
            log.error("拽取电影信息异常 "+e.toString());
            SystemCount.errorCount++;
            return null;
        }

        return dto;
    }
}
