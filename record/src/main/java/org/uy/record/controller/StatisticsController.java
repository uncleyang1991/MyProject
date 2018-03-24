package org.uy.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.service.StatisticsService;
import org.uy.record.tools.JsonTool;


@Controller
@RequestMapping(value="/record/statistics",produces="application/json;charset=utf-8")
public class StatisticsController{

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/watchTimeTrend.do")
    @ResponseBody
    public String watchTimeTrend(@RequestParam String year){
        return JsonTool.obj2json(statisticsService.watchTimeTrend(year));
    }

    @RequestMapping("/episodeTypeTop.do")
    @ResponseBody
    public String episodeTypeTop(){
        return JsonTool.obj2json(statisticsService.episodeTypeTop());
    }

    @RequestMapping("/movieTypeTop.do")
    @ResponseBody
    public String movieTypeTop(){
        return JsonTool.obj2json(statisticsService.movieTypeTop());
    }

    @RequestMapping("/animationTypeTop.do")
    @ResponseBody
    public String animationTypeTop(){
        return JsonTool.obj2json(statisticsService.animationTypeTop());
    }

    @RequestMapping("/totalCount.do")
    @ResponseBody
    public String totalCount(){
        return JsonTool.obj2json(statisticsService.totalCount());
    }

    @RequestMapping("/episodePerformersTop.do")
    @ResponseBody
    public String episodePerformersTop(){
        return JsonTool.obj2json(statisticsService.episodePerformersTop());
    }

    @RequestMapping("/moviePerformersTop.do")
    @ResponseBody
    public String moviePerformersTop(){
        return JsonTool.obj2json(statisticsService.moviePerformersTop());
    }

    @RequestMapping("/animationPerformersTop.do")
    @ResponseBody
    public String animationPerformersTop(){
        return JsonTool.obj2json(statisticsService.animationPerformersTop());
    }

    @RequestMapping("/episodeDramaTypePie.do")
    @ResponseBody
    public String episodeDramaTypePie(){
        return JsonTool.obj2json(statisticsService.episodeDramaTypePie());
    }

    @RequestMapping("/movieRegionPie.do")
    @ResponseBody
    public String movieRegionPie(){
        return JsonTool.obj2json(statisticsService.movieRegionPie());
    }

    @RequestMapping("/animationDramaTypePie.do")
    @ResponseBody
    public String animationDramaTypePie(){
        return JsonTool.obj2json(statisticsService.animationDramaTypePie());
    }

    @RequestMapping("/levelRadar.do")
    @ResponseBody
    public String levelRadar(){
        return JsonTool.obj2json(statisticsService.levelRadar());
    }
}
