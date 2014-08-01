package com.cortez.samples.batchrealworld.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Roberto Cortez
 */
public class GenerateTestFile {
    public static void generateTestFile(String fileName, Integer companyId, Integer sequence, Integer records) {
        try (BufferedWriter writer = Files.newBufferedWriter(new File(fileName).toPath())) {
            writer.write("HEADER," + companyId + "," + sequence + "," + records);
            for (int i = 0; i < records; i++) {
                writer.newLine();
                writer.write("data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
