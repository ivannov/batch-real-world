package com.cortez.samples.batchrealworld.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Roberto Cortez
 */
@Entity
public class CompanyData implements Serializable {
    private static Integer idCounter = 0;

    @Id
    // TODO - check problem with hibernate not selecting next val on each insert
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyDataId")
    //@SequenceGenerator(name = "companyDataId", sequenceName = "COMPANYDATAID")
    private Integer id;

    private Integer companyId;

    private String data;
    private String data2;
    private String data3;
    private String data4;
    private String data5;
    private String data6;
    private String data7;
    private String data8;
    private String data9;

    public CompanyData() {
        this.id = idCounter ++;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getData6() {
        return data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public String getData7() {
        return data7;
    }

    public void setData7(String data7) {
        this.data7 = data7;
    }

    public String getData8() {
        return data8;
    }

    public void setData8(String data8) {
        this.data8 = data8;
    }

    public String getData9() {
        return data9;
    }

    public void setData9(String data9) {
        this.data9 = data9;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CompanyData that = (CompanyData) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompanyData{");
        sb.append("id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", data='").append(data).append('\'');
        sb.append(", data2='").append(data2).append('\'');
        sb.append(", data3='").append(data3).append('\'');
        sb.append(", data4='").append(data4).append('\'');
        sb.append(", data5='").append(data5).append('\'');
        sb.append(", data6='").append(data6).append('\'');
        sb.append(", data7='").append(data7).append('\'');
        sb.append(", data8='").append(data8).append('\'');
        sb.append(", data9='").append(data9).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
