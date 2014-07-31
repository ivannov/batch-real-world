package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;

import static com.cortez.samples.batchrealworld.entity.FolderType.FI_TMP;
import static java.lang.Integer.valueOf;
import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.moveFileToDirectory;

/**
 * @author Roberto Cortez
 */
@Named
public class MoveFileBatchlet extends AbstractBatchlet {
    @Inject
    @BatchProperty(name = "companyId")
    private String companyId;

    @Inject
    @BatchProperty(name = "fileToProcess")
    private String fileToProcess;

    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Override
    public String process() throws Exception {
        System.out.println("MoveFilesBatchlet.process");

        File file = getFile(fileToProcess);
        File destinationFolder = getFile(batchBusinessBean.findCompanyFolderById(valueOf(companyId), FI_TMP).getPath());

        System.out.println("Moving file " + file + " to " + destinationFolder);
        moveFileToDirectory(file, destinationFolder, false);

        return "COMPLETED";
    }
}
