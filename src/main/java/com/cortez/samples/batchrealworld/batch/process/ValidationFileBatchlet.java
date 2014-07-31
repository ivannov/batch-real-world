package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class ValidationFileBatchlet extends AbstractBatchlet {
    @Override
    public String process() throws Exception {
        System.out.println("FileValidationBatchlet.process");

        // HEADER:COMPANY_ID, FILE_SEQUENCE, DATE, NUMBER_OF_RECORDS

        return "ERROR";
    }
}
