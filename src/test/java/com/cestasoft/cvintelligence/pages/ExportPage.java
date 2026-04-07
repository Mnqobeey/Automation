package com.cestasoft.cvintelligence.pages;

import com.cestasoft.cvintelligence.utils.FileUtils;
import com.cestasoft.cvintelligence.utils.WaitUtils;
import org.openqa.selenium.By;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class ExportPage extends BasePage {
    private static final By DOWNLOAD_BUTTON = By.id("downloadPreviewBtn");

    public void downloadDocx() {
        FileUtils.cleanDownloads();
        WaitUtils.clickable(driver, DOWNLOAD_BUTTON).click();
    }

    public Path waitForDownloadedDocx() {
        Instant deadline = Instant.now().plus(Duration.ofSeconds(30));
        while (Instant.now().isBefore(deadline)) {
            try {
                Path file = FileUtils.latestDownloadedDocx();
                if (file.toFile().length() > 0) {
                    return file;
                }
            } catch (Exception ignored) {
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while waiting for download.", exception);
            }
        }
        throw new IllegalStateException("Timed out waiting for DOCX download.");
    }
}
