package com.cortez.samples.batchrealworld.batch.process;

import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.batch.api.chunk.ItemReader;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

/**
 * @author Roberto Cortez
 */
@Named
public class FileItemReader extends AbstractFileProcess implements ItemReader {
    private File fileToProcess;
    private BufferedReader reader;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        fileToProcess = getFileToProcess(FolderType.FI_TMP);
        System.out.println("Starting file to process: " + fileToProcess);
        reader = Files.newBufferedReader(fileToProcess.toPath());
    }

    @Override
    public void close() throws Exception {
        System.out.println("Finished file processing: " + fileToProcess);
        reader.close();
    }

    @Override
    public String readItem() throws Exception {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
