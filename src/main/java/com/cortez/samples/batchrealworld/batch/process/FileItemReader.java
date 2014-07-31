package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemReader extends AbstractItemReader {
    @Override
    public Object readItem() throws Exception {
        return null;
    }
}
