package com.cortez.samples.batchrealworld.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Roberto Cortez
 */
@Entity
public class CompanyFolder implements Serializable {
    @EmbeddedId
    private CompanyFolderPK id;

    private String path;

    public CompanyFolder() {}

    public CompanyFolder(Integer companyId, FolderType type, String path) {
        this.id = new CompanyFolderPK(companyId, type);
        this.path = path;
    }

    public CompanyFolderPK getId() {
        return id;
    }

    public void setId(CompanyFolderPK id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CompanyFolder that = (CompanyFolder) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Embeddable
    public static class CompanyFolderPK implements Serializable {
        @Basic
        private Integer companyId;
        @Enumerated
        private FolderType folderType;

        public CompanyFolderPK() {}

        public CompanyFolderPK(Integer companyId, FolderType folderType) {
            this.companyId = companyId;
            this.folderType = folderType;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public FolderType getFolderType() {
            return folderType;
        }

        public void setFolderType(FolderType folderType) {
            this.folderType = folderType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            CompanyFolderPK that = (CompanyFolderPK) o;

            return companyId.equals(that.companyId) && folderType == that.folderType;
        }

        @Override
        public int hashCode() {
            int result = companyId.hashCode();
            result = 31 * result + folderType.hashCode();
            return result;
        }
    }
}
