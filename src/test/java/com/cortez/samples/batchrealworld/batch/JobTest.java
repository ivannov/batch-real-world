package com.cortez.samples.batchrealworld.batch;

import com.cortez.samples.batchrealworld.business.BatchBusinessBean;
import com.cortez.samples.batchrealworld.configuration.Configuration;
import com.cortez.samples.batchrealworld.entity.CompanyFolder;
import com.cortez.samples.batchrealworld.entity.FolderType;
import org.apache.commons.io.FileUtils;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.cortez.samples.batchrealworld.batch.BatchTestHelper.keepTestAlive;
import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
                                        .resolve("commons-io:commons-io")
                                        .withTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
                                   .addAsLibraries(requiredLibraries)
                                   .addPackages(true, "com.cortez.samples.batchrealworld")
                                   .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                                   .addAsResource("META-INF/persistence.xml")
                                   .addAsResource("META-INF/sql/create.sql")
                                   .addAsResource("META-INF/sql/drop.sql")
                                   .addAsResource("META-INF/sql/load.sql")
                                   .addAsResource("META-INF/batch-jobs/prepare-job.xml")
                                   .addAsResource("META-INF/batch-jobs/process-job.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    @InSequence(1)
    public void testPrepareJob() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("prepare-job", new Properties());

        JobExecution jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }

    @Test
    @InSequence(2)
    public void testCompanyFolders() throws Exception {
        List<CompanyFolder> companyFolders = batchBusinessBean.findCompanyFolders();
        companyFolders.forEach(System.out::println);
        assertFalse(companyFolders.isEmpty());
    }

    @Test
    @InSequence(3)
    public void testProcessJob() throws Exception {
        Optional<String> anyFile = batchBusinessBean.findFilesByFolderType(FolderType.FI).stream().findAny();

        Properties jobParameters = new Properties();
        jobParameters.setProperty("companyId", "1");
        jobParameters.setProperty("fileToProcess", anyFile.get());


        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("process-job", jobParameters);

        JobExecution jobExecution = keepTestAlive(jobOperator, executionId);

        assertEquals(BatchStatus.COMPLETED, jobExecution.getBatchStatus());
    }
}
