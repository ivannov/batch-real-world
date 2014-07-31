package com.cortez.samples.batchrealworld.batch.prepare;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.Company;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;
import org.apache.commons.io.FileUtils;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Roberto Cortez
 */
@Named
public class FolderCreationBatchlet extends AbstractBatchlet {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Inject
    @Configuration
    private String batchHome;
    @Inject
    @Configuration
    private Map<String, List<FolderType>> folders;

    @Override
    public String process() throws Exception {
        System.out.println("FolderCreationBatchlet.process");

        batchBusinessBean.findAllCompanies()
                         .stream()
                         .map(Company::getId)
                         .forEach(companyId -> folders
                                 .forEach((folderRoot, folderTypes) -> folderTypes
                                         .forEach(folderType ->
                                                          verifyAndCreateFolder(companyId, folderRoot, folderType))));

        return "COMPLETED";
    }

    private void verifyAndCreateFolder(Integer companyId, String folderRoot, FolderType folderType) {
        File folder = new File(batchHome + "/" + folderRoot + "/" + companyId + "/" + folderType);

        if (!folder.exists()) {
            try {
                System.out.println("Creating folder " + folder);
                FileUtils.forceMkdir(folder);
                batchBusinessBean.createCompanyFolder(new CompanyFolder(companyId, folderType, folder.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (batchBusinessBean.findCompanyFolderById(companyId, folderType) == null) {
            batchBusinessBean.createCompanyFolder(new CompanyFolder(companyId, folderType, folder.getPath()));
        }
    }
}
