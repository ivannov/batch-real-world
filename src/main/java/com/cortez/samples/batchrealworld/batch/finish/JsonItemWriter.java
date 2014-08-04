package com.cortez.samples.batchrealworld.batch.finish;

import com.cortez.samples.batchrealworld.entity.CompanyData;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;

/**
 * @author Roberto Cortez
 */
@Named
public class JsonItemWriter extends AbstractItemWriter {
    @SuppressWarnings("unchecked")
    @Override
    public void writeItems(List items) throws Exception {
        List<List<CompanyData>> companyDataList = (List<List<CompanyData>>) items;
    }
}
