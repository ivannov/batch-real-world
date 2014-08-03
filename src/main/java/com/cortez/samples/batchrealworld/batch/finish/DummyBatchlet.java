package com.cortez.samples.batchrealworld.batch.finish;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class DummyBatchlet extends AbstractBatchlet {
    @Override
    public String process() throws Exception {
        return "COMPLETED";
    }
}
