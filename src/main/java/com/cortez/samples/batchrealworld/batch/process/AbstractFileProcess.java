package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.annotation.PostConstruct;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.io.File;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * @author Roberto Cortez
 */
public abstract class AbstractFileProcess {
    private String companyId;
    private String fileToProcess;

    @Inject
    private JobContext jobContext;
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

    @PostConstruct
    private void init() {
        companyId = jobContext.getProperties().getProperty("companyId");
        fileToProcess = jobContext.getProperties().getProperty("fileToProcess");
    }
}
