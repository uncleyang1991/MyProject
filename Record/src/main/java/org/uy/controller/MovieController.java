package org.uy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.entity.MovieDto;
import org.uy.page.DataTablesResult;
import org.uy.service.MovieService;
import org.uy.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/movie",produces="text/html;charset=utf-8")
public class MovieController {

    @Resource
    private MovieService service;

    @RequestMapping("/movieList.do")
    @ResponseBody
    public String movieList(@RequestParam Map<String,Object> params){
        DataTablesResult<MovieDto> result = service.findMovieBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addMovie.do")
    @ResponseBody
    public String addMovie(@RequestParam Map<String,Object> params){
        boolean result = service.addMovie(params);
        if(result){
            return JsonTool.makeResultJson(true,"添加成功");
        }
        return JsonTool.makeResultJson(false,"添加失败");
    }

    @RequestMapping("/getMovieInfo.do")
    @ResponseBody
    public String getMovieInfo(@RequestParam String id){
        if(id==null){
            return JsonTool.makeResultJson(false,"读取信息出错!");
        }
        return JsonTool.obj2json(service.findMovieById(id));
    }

    @RequestMapping("/updateMovie.do")
    @ResponseBody
    public String updateMovie(@RequestParam Map<String,Object> params){
        boolean result = service.updateMovie(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/movieInfoPull.do")
    @ResponseBody
    public String movieInfoPull(@RequestParam String id){
        MovieDto movie;
        try{
            movie = service.movieInfoPull(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonTool.makeResultJson(false,"更新失败");
        }
        if(movie != null){
            return JsonTool.obj2json(movie);
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    public void setService(MovieService service) {
        this.service = service;
    }
}
