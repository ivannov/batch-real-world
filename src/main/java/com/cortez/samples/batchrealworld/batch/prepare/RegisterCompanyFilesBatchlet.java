package com.cortez.samples.batchrealworld.batch.prepare;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.CompanyFile;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.listFiles;

/**
 * @author Roberto Cortez
 */
@Named
public class RegisterCompanyFilesBatchlet extends AbstractBatchlet {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Inject
    @Configuration
    private List<String> fileExtensions;

    @Override
    public String process() throws Exception {
        System.out.println("RegisterCompanyFilesBatchlet.process");

        List<CompanyFolder> inputFolders = batchBusinessBean.findCompanyFoldersByType(FolderType.FI);
        inputFolders
                .forEach(inputFolder -> getFiles(inputFolder)
                        .forEach(file -> registerFile(inputFolder, file)));

        return "COMPLETED";
    }

    private void registerFile(CompanyFolder inputFolder, File file) {
        System.out.println("Registering file to process " + file);
        batchBusinessBean.createCompanyFile(new CompanyFile(inputFolder.getId().getCompanyId(), file.getPath()));
    }

    private Collection<File> getFiles(CompanyFolder companyFolder) {
        return listFiles(getFile(companyFolder.getPath()),
                         fileExtensions.stream().toArray(String[]::new),
                         false);
    }
}
