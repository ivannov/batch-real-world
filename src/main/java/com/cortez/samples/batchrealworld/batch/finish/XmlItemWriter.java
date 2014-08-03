package com.cortez.samples.batchrealworld.batch.finish;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Named
public class XmlItemWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println("XmlItemWriter.writeItems");
    }
}
