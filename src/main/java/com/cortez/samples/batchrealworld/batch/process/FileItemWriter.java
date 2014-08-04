package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyData;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemWriter extends AbstractItemWriter {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        items.forEach(o -> batchBusinessBean.createCompanyData(((CompanyData) o)));
    }
}
