package org.uy.record.service;


import org.uy.record.entity.ResultStrDto;
import org.uy.record.page.DataTablesResult;

import java.util.Map;

public interface OperationService {

    DataTablesResult<ResultStrDto> findOperationsBySearchItem(Map<String, Object> item);
}
