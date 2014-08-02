package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.entity.CompanyData;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemProcessor extends AbstractFileProcess implements ItemProcessor {
    @Override
    public Object processItem(Object item) throws Exception {
        String line = (String) item;
        String[] data = line.split(",");

        CompanyData companyData = null;
        if (data.length == 10) {
            companyData = new CompanyData();
            companyData.setCompanyId(getCompanyId());
            companyData.setData(data[0]);
            companyData.setData2(data[1]);
            companyData.setData3(data[2]);
            companyData.setData4(data[3]);
            companyData.setData5(data[4]);
            companyData.setData6(data[5]);
            companyData.setData7(data[6]);
            companyData.setData8(data[7]);
            companyData.setData9(data[8]);
        }

        return companyData;
    }
}
