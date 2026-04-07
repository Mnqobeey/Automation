package com.cestasoft.cvintelligence.utils;

import com.cestasoft.cvintelligence.config.TestConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public final class FileUtils {
    private FileUtils() {
    }

    public static Path resolveTestData(String fileName) {
        return Path.of("src", "test", "resources", "testdata", fileName).toAbsolutePath();
    }

    public static String readTestData(String fileName) {
        try {
            return Files.readString(resolveTestData(fileName));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test data file: " + fileName, exception);
        }
    }

    public static void cleanDownloads() {
        Path directory = TestConfig.getInstance().getDownloadDirectory();
        try {
            Files.createDirectories(directory);
            Files.list(directory).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ignored) {
                }
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to clean downloads directory.", exception);
        }
    }

    public static Path latestDownloadedDocx() {
        Path directory = TestConfig.getInstance().getDownloadDirectory();
        try {
            return Files.list(directory)
                    .filter(path -> path.getFileName().toString().endsWith(".docx"))
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()))
                    .orElseThrow(() -> new IllegalStateException("No DOCX file was downloaded."));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to inspect downloads directory.", exception);
        }
    }
}
