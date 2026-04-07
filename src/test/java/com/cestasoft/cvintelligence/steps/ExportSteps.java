package com.cestasoft.cvintelligence.steps;

import com.cestasoft.cvintelligence.assertions.DocxAssertions;
import com.cestasoft.cvintelligence.pages.ExportPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.nio.file.Path;

public class ExportSteps {
    private final ExportPage exportPage = new ExportPage();
    private Path downloadedFile;

    @And("I download the DOCX profile")
    public void iDownloadTheDocxProfile() {
        exportPage.downloadDocx();
        downloadedFile = exportPage.waitForDownloadedDocx();
    }

    @Then("a DOCX file is downloaded")
    public void aDocxFileIsDownloaded() {
        DocxAssertions.assertValidDocx(downloadedFile);
    }
}
