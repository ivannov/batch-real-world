package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.BatchProperty;
import javax.inject.Inject;
import java.io.File;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * @author Roberto Cortez
 */
public abstract class AbstractFileProcess {
    @Inject
    @BatchProperty(name = "companyId")
    private String companyId;
    @Inject
    @BatchProperty(name = "fileToProcess")
    private String fileToProcess;

    @Inject
    private BatchBusinessBean batchBusinessBean;

    protected Integer getCompanyId() {
        return Integer.valueOf(companyId);
    }

    protected File getFileToProcess(FolderType folderType) {
        return getFile(batchBusinessBean.findCompanyFolderById(getCompanyId(), folderType).getPath() +
                       "/" +
                       fileToProcess);
    }

    protected File getFolder(FolderType folderType) {
        return getFile(batchBusinessBean.findCompanyFolderById(getCompanyId(), folderType).getPath());
    }
}
