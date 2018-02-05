package org.uy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.entity.MovieDto;
import org.uy.page.DataTablesResult;
import org.uy.service.MovieService;
import org.uy.service.StatisticsService;
import org.uy.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/statistics",produces="text/html;charset=utf-8")
public class StatisticsController {

    @Resource
    private StatisticsService service;

    @RequestMapping("/watchTimeTrend.do")
    @ResponseBody
    public String watchTimeTrend(@RequestParam String year){
        return JsonTool.obj2json(service.watchTimeTrend(year));
    }

    @RequestMapping("/episodeTypeTop.do")
    @ResponseBody
    public String episodeTypeTop(){
        return JsonTool.obj2json(service.episodeTypeTop());
    }

    @RequestMapping("/movieTypeTop.do")
    @ResponseBody
    public String movieTypeTop(){
        return JsonTool.obj2json(service.movieTypeTop());
    }

    @RequestMapping("/animationTypeTop.do")
    @ResponseBody
    public String animationTypeTop(){
        return JsonTool.obj2json(service.animationTypeTop());
    }

    @RequestMapping("/totalCount.do")
    @ResponseBody
    public String totalCount(){
        return JsonTool.obj2json(service.totalCount());
    }

    @RequestMapping("/episodePerformersTop.do")
    @ResponseBody
    public String episodePerformersTop(){
        return JsonTool.obj2json(service.episodePerformersTop());
    }

    @RequestMapping("/moviePerformersTop.do")
    @ResponseBody
    public String moviePerformersTop(){
        return JsonTool.obj2json(service.moviePerformersTop());
    }

    @RequestMapping("/animationPerformersTop.do")
    @ResponseBody
    public String animationPerformersTop(){
        return JsonTool.obj2json(service.animationPerformersTop());
    }

    @RequestMapping("/episodeDramaTypePie.do")
    @ResponseBody
    public String episodeDramaTypePie(){
        return JsonTool.obj2json(service.episodeDramaTypePie());
    }

    @RequestMapping("/movieRegionPie.do")
    @ResponseBody
    public String movieRegionPie(){
        return JsonTool.obj2json(service.movieRegionPie());
    }

    @RequestMapping("/animationDramaTypePie.do")
    @ResponseBody
    public String animationDramaTypePie(){
        return JsonTool.obj2json(service.animationDramaTypePie());
    }

    @RequestMapping("/levelRadar.do")
    @ResponseBody
    public String levelRadar(){
        return JsonTool.obj2json(service.levelRadar());
    }

    public void setService(StatisticsService service) {
        this.service = service;
    }
}
