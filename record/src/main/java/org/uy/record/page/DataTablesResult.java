package org.uy.record.page;

import java.io.Serializable;
import java.util.List;

public class DataTablesResult<E> implements Serializable{

    private static final long serialVersionUID = -328963713727035205L;

    private int recordsTotal;

    private int recordsFiltered;

    private List<E> data;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
