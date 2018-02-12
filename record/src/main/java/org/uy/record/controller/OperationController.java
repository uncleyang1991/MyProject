package org.uy.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.ResultStrDto;
import org.uy.record.page.DataTablesResult;
import org.uy.record.service.OperationService;
import org.uy.record.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/record/operation",produces="application/json;charset=utf-8")
public class OperationController {

    @Resource
    private OperationService service;

    @RequestMapping("/operationList.do")
    @ResponseBody
    public String operationList(@RequestParam Map<String,Object> params){
        DataTablesResult<ResultStrDto> result = service.findOperationsBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    public void setService(OperationService service) {
        this.service = service;
    }
}
