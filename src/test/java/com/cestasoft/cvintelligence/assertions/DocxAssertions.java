package com.cestasoft.cvintelligence.assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public final class DocxAssertions {
    private DocxAssertions() {
    }

    public static void assertValidDocx(Path file) {
        if (!Files.exists(file)) {
            throw new AssertionError("Expected DOCX file does not exist: " + file);
        }
        if (!file.getFileName().toString().endsWith(".docx")) {
            throw new AssertionError("Expected a .docx file but got: " + file.getFileName());
        }
        try (ZipFile zip = new ZipFile(file.toFile())) {
            if (zip.getEntry("[Content_Types].xml") == null) {
                throw new AssertionError("Downloaded file is not a valid DOCX package.");
            }
        } catch (IOException exception) {
            throw new AssertionError("Downloaded file could not be opened as DOCX.", exception);
        }
    }
}
