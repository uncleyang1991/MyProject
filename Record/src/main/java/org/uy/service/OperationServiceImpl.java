package org.uy.service;

import org.springframework.stereotype.Service;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.dao.OperationDao;
import org.uy.entity.OperationDto;
import org.uy.entity.ResultStrDto;
import org.uy.page.DataTablesResult;
import org.uy.tools.DateFormatTool;
import org.uy.tools.JsonTool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationServiceImpl implements OperationService{

    @Resource
    private OperationDao operationDao;

    @Override
    public DataTablesResult<ResultStrDto> findOperationsBySearchItem(Map<String, Object> item) {
        DataTablesResult<ResultStrDto> dataTablesResult = new DataTablesResult<ResultStrDto>();
        PageParameter parameter = new PageParameter();
        parameter.setPageSize(Integer.parseInt(item.get("length").toString()));
        parameter.setPage(Integer.parseInt(item.get("start").toString())/Integer.parseInt(item.get("length").toString())+1);
        PageResult<OperationDto> pageResult = operationDao.findOperationBySearchItem(item,parameter);
        dataTablesResult.setRecordsTotal(pageResult.getTotalRow());
        dataTablesResult.setRecordsFiltered(pageResult.getTotalRow());
        List<ResultStrDto> resultStr = new ArrayList<ResultStrDto>();
        for(OperationDto dto:pageResult){
            if("add".equals(dto.getAction())){
                resultStr.add(new ResultStrDto("row",DateFormatTool.dateToStr("yyyy-MM-dd HH:mm:ss",dto.getActionTime())+"&nbsp;增加了新"+dto.getType()+"《"+dto.getName()+"》"));
            }else if("update".equals(dto.getAction())){
                resultStr.add(new ResultStrDto("row",DateFormatTool.dateToStr("yyyy-MM-dd HH:mm:ss",dto.getActionTime())+"&nbsp;更新了"+dto.getType()+"《"+dto.getName()+"》"));
            }

        }
        dataTablesResult.setData(resultStr);
        return dataTablesResult;
    }
}
