package com.cortez.samples.batchrealworld.batch.prepare;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.listener.AbstractStepListener;
import javax.inject.Inject;
import javax.inject.Named;

import static com.cortez.samples.batchrealworld.generator.GenerateTestFile.generateInvalidTestFile;
import static com.cortez.samples.batchrealworld.generator.GenerateTestFile.generateValidTestFile;

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
        generateValidTestFile(companyFolder.getPath() + "/valid.dat", companyFolder.getId().getCompanyId(), 1, 100);
        generateInvalidTestFile(companyFolder.getPath() + "/invalid.dat", companyFolder.getId().getCompanyId(), 1, 100);
    }
}
