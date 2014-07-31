package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object item) throws Exception {
        return null;
    }
}
