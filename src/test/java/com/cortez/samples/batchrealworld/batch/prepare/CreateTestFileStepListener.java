package com.cortez.samples.batchrealworld.batch.prepare;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.listener.AbstractStepListener;
import javax.inject.Inject;
import javax.inject.Named;

import static com.cortez.samples.batchrealworld.generator.GenerateTestFile.generateTestFile;

/**
 * @author Roberto Cortez
 */
@Named
public class CreateTestFileStepListener extends AbstractStepListener {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Override
    public void beforeStep() throws Exception {
        CompanyFolder companyFolder = batchBusinessBean.findCompanyFolderById(1, FolderType.FI);
        generateTestFile(companyFolder.getPath() + "/test.dat", companyFolder.getId().getCompanyId(), 1, 100);
    }
}
