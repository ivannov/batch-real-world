package com.cortez.samples.batchrealworld.batch.finish;

import com.cortez.samples.batchrealworld.entity.CompanyData;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Named
public class JsonItemWriter extends AbstractItemWriter {
    @SuppressWarnings("unchecked")
    @Override
    public void writeItems(List items) throws Exception {
        System.out.println("JsonItemWriter.writeItems");
        List<List<CompanyData>> companyDataList = (List<List<CompanyData>>) items;
        //companyDataList.forEach(System.out::println);
    }
}
