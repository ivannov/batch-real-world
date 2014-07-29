package com.cortez.samples.batchrealworld.business;

import com.cortez.samples.batchrealworld.entity.Company;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
}
