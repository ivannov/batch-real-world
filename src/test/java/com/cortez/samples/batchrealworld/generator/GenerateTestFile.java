package com.cortez.samples.batchrealworld.generator;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.StringJoiner;

/**
 * @author Roberto Cortez
 */
public class GenerateTestFile {
    public static void generateValidTestFile(String fileName, Integer companyId, Integer sequence, Integer records) {
        generateTestFile(fileName, companyId, sequence, records, records);
    }

    public static void generateInvalidTestFile(String fileName, Integer companyId, Integer sequence, Integer records) {
        generateTestFile(fileName, companyId, sequence, records, records - 1);
    }

    private static void generateTestFile(String fileName, Integer companyId, Integer sequence, Integer records,
                                         Integer lines) {
        try (BufferedWriter writer = Files.newBufferedWriter(new File(fileName).toPath())) {
            writer.write("HEADER," + companyId + "," + sequence + "," + records);
            for (int i = 0; i < lines; i++) {
                writer.newLine();
                StringJoiner dataRecord = new StringJoiner(",");
                for (int j = 0; j < 10; j++) {
                    dataRecord.add(RandomStringUtils.randomAlphanumeric(100));
                }
                writer.write(dataRecord.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
