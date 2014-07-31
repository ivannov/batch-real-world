package com.cortez.samples.batchrealworld.business;

import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.Company;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.listFiles;

/**
 * @author Roberto Cortez
 */
@SuppressWarnings("unchecked")
@Local
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BatchBusinessBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    @Configuration
    private List<String> fileExtensions;

    public List<Company> findAllCompanies() {
        return entityManager.createQuery("SELECT c FROM Company c").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCompanyFolder(CompanyFolder companyFolder) {
        entityManager.persist(companyFolder);
    }

    public CompanyFolder findCompanyFolderById(Integer companyId, FolderType folderType) {
        return entityManager.find(CompanyFolder.class, new CompanyFolder.CompanyFolderPK(companyId, folderType));
    }

    public List<CompanyFolder> findCompanyFolders() {
        return entityManager.createQuery("SELECT cf FROM CompanyFolder cf").getResultList();
    }

    public List<CompanyFolder> findCompanyFoldersByType(FolderType type) {
        return entityManager.createQuery("SELECT cf FROM CompanyFolder cf WHERE cf.id.folderType = :type")
                            .setParameter("type", type)
                            .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<String> findFilesByFolderType(FolderType folderType) {
        List<CompanyFolder> companyFolders = findCompanyFolders();

        Stream<Collection<File>> files =
                companyFolders.stream().map(companyFolder -> listFiles(getFile(companyFolder.getPath()),
                                                                       fileExtensions.stream().toArray(String[]::new),
                                                                       false));

        Optional<Collection<File>> reduce = files.reduce((files1, files2) -> {
            files1.addAll(files2);
            return files1;
        });

        return reduce.orElseGet(ArrayList::new)
                     .stream()
                     .map(File::getPath)
                     .collect(Collectors.toCollection(ArrayList::new));
    }
}
