package com.cortez.samples.batchrealworld.batch.finish;

import javax.batch.api.Decider;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;

/**
 * @author Roberto Cortez
 */
@Named
public class FileTypeWriterDecider implements Decider {
    @Inject
    private JobContext jobContext;

    @Override
    public String decide(StepExecution[] executions) throws Exception {
        String fileType = jobContext.getProperties().getProperty("fileType");
        getLogger(this.getClass().getName()).log(Level.INFO, "Writing " + fileType + " file");
        return "json".equals(fileType) ? "json" : "xml";
    }
}
