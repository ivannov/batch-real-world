package com.cortez.samples.batchrealworld.business;

import com.cortez.samples.batchrealworld.entity.Company;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Roberto Cortez
 */
@Local
public class BatchBusinessBean {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Company> findAllCompanies() {
        return entityManager.createQuery("SELECT c FROM Company c").getResultList();
    }
}
