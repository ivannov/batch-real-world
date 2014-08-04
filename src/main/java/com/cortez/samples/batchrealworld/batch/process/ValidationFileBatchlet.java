package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.Batchlet;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

import static com.cortez.samples.batchrealworld.entity.FolderType.FI_TMP;
import static java.util.logging.Logger.getLogger;

/**
 * @author Roberto Cortez
 */
@Named
public class ValidationFileBatchlet extends AbstractFileProcess implements Batchlet {
    @Override
    public String process() throws Exception {
        File file = getFileToProcess(FI_TMP);
        getLogger(this.getClass().getName()).log(Level.INFO, "Validating file" + file.getPath());

        String exitStatus = "ERROR";
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            Header header = extractHeader(reader.readLine());
            if (header.isValid()) {
                int lines = 0;
                while (reader.readLine() != null) lines++;

                if (lines == header.getRecords()) {
                    exitStatus = "SUCCESS";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exitStatus;
    }

    @Override
    public void stop() throws Exception {}

    private Header extractHeader(String header) {
        String[] split = header.split(",");

        if (split.length == 4 && "HEADER".equals(split[0])) {
            // TODO - Number Format Exception.
            return new Header(Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
        } else {
            return new Header(false);
        }
    }

    private class Header {
        private Integer companyId;
        private Integer sequenceId;
        private Integer records;
        private boolean valid;

        private Header(boolean valid) {
            this.valid = valid;
        }

        private Header(Integer companyId, Integer sequenceId, Integer records) {
            this.companyId = companyId;
            this.sequenceId = sequenceId;
            this.records = records;
            this.valid = true;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public Integer getSequenceId() {
            return sequenceId;
        }

        public Integer getRecords() {
            return records;
        }

        public boolean isValid() {
            return valid;
        }
    }
}
