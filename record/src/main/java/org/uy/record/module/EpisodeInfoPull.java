package org.uy.record.module;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.system.SystemCount;
import org.uy.record.tools.DateFormatTool;

/**
 * 从豆瓣网上拉取剧集信息
 */

@Component
public class EpisodeInfoPull {

    private final Logger log = Logger.getLogger(EpisodeInfoPull.class);

    private String BASE_URL = "https://movie.douban.com/subject/";

    public EpisodeDto getEpisodeInfo(String id){
        EpisodeDto dto = new EpisodeDto();

        try {
            //开始抓取
            Document document = Jsoup.connect(BASE_URL+id).post();
            Document celDoc = Jsoup.connect(BASE_URL+id+"/celebrities").post();

            //剧名
            String name = celDoc.title();
            name = name.substring(0,name.lastIndexOf(" "));
            dto.setName(name.trim());

            //类型
            StringBuilder type = new StringBuilder();
            for(int i=7;i<=document.getElementById("info").children().size();i++){
                if(!document.getElementById("info").children().get(i).hasText()){
                    break;
                }
                type.append(document.getElementById("info").children().get(i).text()).append(",");
            }
            dto.setType(type.substring(0,type.length()-1));

            //编剧
            StringBuffer writer = new StringBuffer();
            for(Element e:document.getElementById("info").child(2).children().get(1).children()){
                String w = e.text();
                if(w.contains("（")){
                    w = w.substring(0,w.indexOf("（"));
                }
                writer.append(w.trim()).append(",");
            }
            dto.setWriter(writer.substring(0,writer.length()-1));

            String str = document.getElementById("info").text();

            //集数
            if(str.contains("集数:")){
                String total = str.substring(str.lastIndexOf("集数:")+3,str.lastIndexOf("集数:")+6);
                dto.setTotal(Integer.parseInt(total.trim()));
            }

            //种类
            if(str.contains("制片国家/地区:")){
                String dramaType = str.substring(str.lastIndexOf("制片国家/地区:")+8,str.lastIndexOf("制片国家/地区:")+11).trim();
                if("日本".equals(dramaType)){
                    dto.setDramaType("日剧");
                }else if("美国".equals(dramaType)){
                    dto.setDramaType("美剧");
                }else if("中国".equals(dramaType)){
                    dto.setDramaType("国产剧");
                }else if("韩国".equals(dramaType)){
                    dto.setDramaType("韩剧");
                }else if("英国".equals(dramaType)){
                    dto.setDramaType("英剧");
                }
            }

            //演员
            StringBuilder performers = new StringBuilder();
            for(Element h2:celDoc.getElementsByTag("h2")){
                if(h2.text().startsWith("演员")){
                    Element ul = h2.nextElementSibling();
                    Elements lis = ul.select("li");
                    for(Element e:lis){
                        String p = e.child(0).attr("title").split(" ")[0];
                        if(p.contains("（")){
                            p = p.substring(0,p.indexOf("（"));
                        }
                        performers.append(p.trim()+",");
                    }
                    dto.setPerformers(performers.substring(0,performers.length()-1));
                    break;
                }
            }


            //播出时间
            if(str.contains("首播:")){
                String showTime = str.substring(str.lastIndexOf("首播:")+4,str.lastIndexOf("首播:")+14);
                dto.setShowTime(DateFormatTool.strToDate(null,showTime.trim()));
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
            log.error("拽取剧集信息异常 "+e.toString());
            SystemCount.errorCount++;
            return null;
        }

        return dto;
    }

    public static void main(String[] args) throws Exception{
        Document document = Jsoup.connect("https://movie.douban.com/subject_search?search_text=%E5%8F%98%E5%BD%A2%E9%87%91%E5%88%9A").get();
        System.out.println(document);
    }

}
