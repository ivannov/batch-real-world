package com.cortez.samples.batchrealworld.batch;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;

/**
 * @author Roberto Cortez
 */
@Named
public class MoveFilesBatchlet extends AbstractBatchlet {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Inject
    @Configuration
    private List<String> fileExtensions;

    @Override
    public String process() throws Exception {
        System.out.println("MoveFilesBatchlet.process");

        batchBusinessBean.findCompanyFoldersByType(FolderType.FI)
                         .forEach(this::moveFiles);

        return "COMPLETED";
    }

    protected void moveFiles(CompanyFolder companyFolder) {
        System.out.println("Processing folder " + companyFolder.getPath());

        Collection<File> files = listFiles(getFile(companyFolder.getPath()),
                                           fileExtensions.stream().toArray(String[]::new),
                                           false);

        files.stream().forEach(file -> moveFile(file, companyFolder));
    }

    private void moveFile(File file, CompanyFolder companyFolder) {
        try {
            File destinationFolder =
                    getFile(batchBusinessBean.findCompanyFolderById(companyFolder.getId().getCompanyId(),
                                                                    FolderType.FI_TMP).getPath());

            System.out.println("Moving file " + file + " to " + destinationFolder);
            moveFileToDirectory(file, destinationFolder, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
