package org.uy.record.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/record/douban",produces="application/json;charset=utf-8")
public class DoubanController {

    @RequestMapping("/search.do")
    @ResponseBody
    public String search(@RequestParam("name") String name) throws Exception{
        String url = "https://movie.douban.com/j/subject_suggest?q=" + name;
        Document document = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").get();
        String data = document.text();
        data = data.replace("\\","");
        return data;
    }
}
