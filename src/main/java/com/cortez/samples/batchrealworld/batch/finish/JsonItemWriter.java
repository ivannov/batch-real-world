package com.cortez.samples.batchrealworld.batch.finish;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyData;
import com.cortez.samples.batchrealworld.entity.CompanyFile;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;

/**
 * @author Roberto Cortez
 */
@Named
public class JsonItemWriter extends AbstractItemWriter {

    @Inject
    private BatchBusinessBean batchBusinessBean;

    @SuppressWarnings("unchecked")
    @Override
    public void writeItems(List items) throws Exception {
       List<List<CompanyData>> companyDataList = (List<List<CompanyData>>) items;
       companyDataList.forEach(list -> list.forEach(company -> buildJsonForCompanyData(company) ));
    }

   private void buildJsonForCompanyData(CompanyData company)
   {
      JsonObject jsonObject = companyToJson(company);
      CompanyFolder folder = batchBusinessBean.findCompanyFolderById(company.getCompanyId(), FolderType.FO_TMP);
      List<CompanyFile> companyFiles = batchBusinessBean.findCompanyFiles(company.getCompanyId());
      companyFiles.forEach(file ->  saveJsonObject(jsonObject, new File(folder.getPath(), file.getFilePath())) );
   }

   private JsonObject companyToJson(CompanyData company)
   {
      return Json.createObjectBuilder()
                  .add("data", company.getData())
                  .add("data2", company.getData2())
                  .add("data3", company.getData3())
                  .add("data4", company.getData4())
                  .add("data5", company.getData5())
                  .add("data6", company.getData6())
                  .add("data7", company.getData7())
                  .add("data8", company.getData8())
                  .add("data9", company.getData9())
                  .build();
   }

   private void saveJsonObject(JsonObject jsonObject, File fileName)
   {
      try (FileWriter fw = new FileWriter(fileName)) {
         JsonWriter jw = Json.createWriter(fw);
         jw.writeObject(jsonObject);
         jw.close();
      } catch (IOException ioe) {
         throw new RuntimeException(ioe);
      }
   }
}
