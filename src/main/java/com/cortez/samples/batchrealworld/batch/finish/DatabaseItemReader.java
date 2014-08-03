package com.cortez.samples.batchrealworld.batch.finish;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyData;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Named
public class DatabaseItemReader extends AbstractItemReader {
    @Inject
    @BatchProperty(name = "companyId")
    private String companyId;

    @Inject
    private BatchBusinessBean batchBusinessBean;

    private int reads = 0;

    @Override
    public Object readItem() throws Exception {
        List<CompanyData> companyData = batchBusinessBean.findCompanyData(Integer.valueOf(companyId),reads++, 10);
        return companyData.isEmpty() ? null : companyData;
    }
}
