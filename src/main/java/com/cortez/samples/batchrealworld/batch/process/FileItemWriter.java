package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> items) throws Exception {

    }
}
