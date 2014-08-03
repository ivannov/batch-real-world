package com.cortez.samples.batchrealworld.batch;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.entity.CompanyFile;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.cortez.samples.batchrealworld.batch.BatchTestHelper.keepTestAlive;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.getFile;
import static org.junit.Assert.assertEquals;

/**
 * @author Roberto Cortez
 */
@RunWith(Arquillian.class)
public class JobTest {
    @Inject
    private BatchBusinessBean batchBusinessBean;

    @Deployment
    public static WebArchive createDeployment() {
        File[] requiredLibraries = Maven.resolver().loadPomFromFile("pom.xml")
                                        .resolve("commons-io:commons-io", "org.apache.commons:commons-lang3")
                                        .withTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                                   .addAsLibraries(requiredLibraries)
                                   .addPackages(true, "com.cortez.samples.batchrealworld")
                                   .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                                   .addAsResource("META-INF/persistence.xml")
                                   .addAsResource("META-INF/sql/create.sql")
                                   .addAsResource("META-INF/sql/drop.sql")
                                   .addAsResource("META-INF/sql/load.sql")
                                   .addAsResource("META-INF/batch-jobs/prepare-job-test.xml")
                                   .addAsResource("META-INF/batch-jobs/process-job.xml")
                                   .addAsResource("META-INF/batch-jobs/finish-job.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    @InSequence(1)
    public void testPrepareJob() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("prepare-job-test", new Properties());

        JobExecution jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }

    @Test
    @InSequence(2)
    public void testProcessJob() throws Exception {
        // Valid File
        Optional<CompanyFile> validCompanyFile =
                batchBusinessBean.findCompanyFiles(1)
                                 .stream()
                                 .filter(filtered -> "valid.dat".equals(filtered.getFilePath()))
                                 .findAny();

        Properties jobParameters = new Properties();
        jobParameters.setProperty("companyId", validCompanyFile.get().getCompanyId().toString());
        jobParameters.setProperty("fileToProcess", validCompanyFile.get().getFilePath());

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("process-job", jobParameters);
        JobExecution jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
        assertEquals(100L, (long) batchBusinessBean.countCompanyDate());

        // Invalid File
        Optional<CompanyFile> invalidCompanyFile =
                batchBusinessBean.findCompanyFiles(1)
                                 .stream()
                                 .filter(filtered -> "invalid.dat".equals(filtered.getFilePath()))
                                 .findAny();

        jobParameters = new Properties();
        jobParameters.setProperty("companyId", invalidCompanyFile.get().getCompanyId().toString());
        jobParameters.setProperty("fileToProcess", invalidCompanyFile.get().getFilePath());

        jobOperator = BatchRuntime.getJobOperator();
        executionId = jobOperator.start("process-job", jobParameters);
        jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }

    @Test
    @InSequence(3)
    public void testFinishJob() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = new Properties();
        jobParameters.setProperty("fileType", "xml");
        jobParameters.setProperty("companyId", "1");
        Long executionId = jobOperator.start("finish-job", jobParameters);

        JobExecution jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }

    @Test
    @InSequence(4)
    public void testCleanupResources() {
        List<CompanyFolder> companyFolders = batchBusinessBean.findCompanyFolders();
        companyFolders.stream().map(CompanyFolder::getPath).forEach(path -> {
            System.out.println("Deleting folder " + path);
            deleteQuietly(getFile(path));
        });
    }
}
