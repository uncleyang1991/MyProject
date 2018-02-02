package org.uy.module;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.uy.entity.AnimationDto;
import org.uy.tools.DateFormatTool;

/**
 * 从豆瓣网上拉取剧集信息
 */
public class AnimationInfoPull {

    private String BASE_URL = "https://movie.douban.com/subject/";

    public AnimationDto getAnimationInfo(String id) throws Exception{
        AnimationDto dto = new AnimationDto();

        //开始抓取
        Document document = Jsoup.connect(BASE_URL+id).post();
        Document celDoc = Jsoup.connect(BASE_URL+id+"/celebrities").post();

        //剧名
        String name = celDoc.title();
        name = name.substring(0,name.lastIndexOf(" 全部影人"));
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
                dto.setDramaType("日漫");
            }else if("中国".equals(dramaType)){
                dto.setDramaType("国漫");
            }else if("美国".equals(dramaType)){
                dto.setDramaType("美漫");
            }
        }

        //演员
        StringBuilder performers = new StringBuilder();
        for(Element h2:celDoc.getElementsByTag("h2")){
            if("演员 Actor/Actress".equals(h2.text())){
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

        return dto;
    }

    public static void main(String[] args) {
        try{
            AnimationDto e = new AnimationInfoPull().getAnimationInfo("2147121");
            System.out.println(e.getPerformers());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
