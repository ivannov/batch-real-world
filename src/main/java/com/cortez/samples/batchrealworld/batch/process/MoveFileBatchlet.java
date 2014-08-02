package com.cortez.samples.batchrealworld.batch.process;

import javax.batch.api.Batchlet;
import javax.inject.Named;
import java.io.File;

import static com.cortez.samples.batchrealworld.entity.FolderType.FI;
import static com.cortez.samples.batchrealworld.entity.FolderType.FI_TMP;
import static org.apache.commons.io.FileUtils.moveFileToDirectory;

/**
 * @author Roberto Cortez
 */
@Named
public class MoveFileBatchlet extends AbstractFileProcess implements Batchlet {
    @Override
    public String process() throws Exception {
        System.out.println("MoveFilesBatchlet.process");

        File file = getFileToProcess(FI);
        File destinationFolder = getFolder(FI_TMP);

        System.out.println("Moving file " + file + " to " + destinationFolder);
        moveFileToDirectory(file, destinationFolder, false);

        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {}
}
