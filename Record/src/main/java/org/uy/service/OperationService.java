package org.uy.service;

import org.uy.entity.ResultStrDto;
import org.uy.page.DataTablesResult;

import java.util.Map;

public interface OperationService {

    DataTablesResult<ResultStrDto> findOperationsBySearchItem(Map<String, Object> item);
}
