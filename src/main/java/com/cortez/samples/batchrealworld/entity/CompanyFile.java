package com.cortez.samples.batchrealworld.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Roberto Cortez
 */
@Entity
public class CompanyFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyFileId")
    @SequenceGenerator(name = "companyFileId", sequenceName = "COMPANYFILEID")
    private Integer id;

    private Integer companyId;

    private String filePath;

    public CompanyFile() {}

    public CompanyFile(Integer companyId, String filePath) {
        this.companyId = companyId;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CompanyFile that = (CompanyFile) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompanyFile{");
        sb.append("id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", filePath='").append(filePath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
