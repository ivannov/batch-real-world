package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
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
    @Inject
    @BatchProperty(name = "from")
    private String from;
    @Inject
    @BatchProperty(name = "to")
    private String to;

    @Override
    public String process() throws Exception {
        System.out.println("MoveFilesBatchlet.process");

        File file = getFileToProcess(FolderType.valueOf(from));
        File destinationFolder = getFolder(FolderType.valueOf(to));

        System.out.println("Moving file " + file + " to " + destinationFolder);
        moveFileToDirectory(file, destinationFolder, false);

        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {}
}
