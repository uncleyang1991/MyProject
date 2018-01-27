package org.uy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.entity.EpisodeDto;
import org.uy.page.DataTablesResult;
import org.uy.service.EpisodeService;
import org.uy.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/episode",produces="text/html;charset=utf-8")
public class EpisodeController {

    @Resource
    private EpisodeService service;

    @RequestMapping("/episodeList.do")
    @ResponseBody
    public String episodeList(@RequestParam Map<String,Object> params){
        DataTablesResult<EpisodeDto> result = service.findEpisodeBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addEpisode.do")
    @ResponseBody
    public String addEpisode(@RequestParam Map<String,Object> params){
        boolean result = service.addEpisode(params);
        if(result){
            return JsonTool.makeResultJson(true,"添加成功");
        }
        return JsonTool.makeResultJson(false,"添加失败");
    }

    @RequestMapping("/getEpisodeInfo.do")
    @ResponseBody
    public String getEpisodeInfo(@RequestParam String id){
        if(id==null){
            return JsonTool.makeResultJson(false,"读取信息出错!");
        }
        return JsonTool.obj2json(service.findEpisodeById(id));
    }

    @RequestMapping("/updateEpisode.do")
    @ResponseBody
    public String updateEpisode(@RequestParam Map<String,Object> params){
        boolean result = service.updateEpisode(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/episodeInfoPull.do")
    @ResponseBody
    public String episodeInfoPull(@RequestParam String id){
        EpisodeDto episode = null;
        try{
            episode = service.episodeInfoPull(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonTool.makeResultJson(false,"更新失败");
        }
        if(episode != null){
            return JsonTool.obj2json(episode);
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    public void setService(EpisodeService service) {
        this.service = service;
    }
}
