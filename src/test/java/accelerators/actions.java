package accelerators;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.ExceptionHandler;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static accelerators.Base.driver;
import static org.junit.Assert.assertTrue;

public class actions {

    public static String sTestCaseName;
    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads/"; // Default downloads folder


    public static void click(By object) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                driver.findElement(object).click();
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Click on element ");
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed...");
        }
    }
    public static String getElementText(By object,String elementName) throws Exception {
        try {
            if(!driver.findElements(object).isEmpty()) {
                return driver.findElement(object).getText();
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element " + elementName);
            }
        }
        catch (Exception e) {
            GetScreenShot();
            ExceptionHandler.HandleException(e, "Failed to get text from element: " + elementName);
        }
        return "";
    }
    public static void typeInTextBox(By object, String data) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                driver.findElement(object).clear();
                driver.findElement(object).sendKeys(data);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element...");
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to enter data...");
        }
    }
    public static boolean isElementVisible(By object, String sVisibletext) throws Exception {
        try {
            if(!driver.findElements(object).isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            GetScreenShot();
            ExceptionHandler.HandleException(e, "Failed to check if element is visible");
        }
        return false;
    }


    public static boolean waitForElement(By Locator, Duration lTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, lTime);
            wait.until(ExpectedConditions.elementToBeClickable(Locator));
            return true;
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to wait for element to be visible");
            return false;
        }
    }
    public static boolean waitForElementToBeVisible(By locator, long timeInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("Element not visible: " + locator + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean isMenuSelected(By object,String elementName) {
        boolean selected=false;
        try {
            if(!driver.findElements(object).isEmpty()) {
                selected= true;
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Display element" + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select Menu:" + elementName);
        }
        return selected;
    }

    public static void CompareUIContent(String data, By object,String elementName) {
        try {
            String text = getElementText(object,elementName).toLowerCase();
            //				SMSC_ExceptionHandler.HandleAssertion(elementName +" is invalidated (is not equals)");
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Unable to compare UI Content for " + elementName);
        }
    }
    public static void ComparePDFWithUI(String AtcualText, By object,String elementName) {
        try {
            if(!AtcualText.toLowerCase().contains(getElementText(object,elementName).toLowerCase())) {
                ExceptionHandler.HandleAssertion(elementName +" is invalid");
            }
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to compare Pdf and UI : "+elementName);
        }
    }
    public static void ComparePDFWithUserInputData(String AtcualText, String Data,String elementName) {
        try {
            if(!AtcualText.toLowerCase().trim().contains(Data.toLowerCase().trim()))
            {
                ExceptionHandler.HandleAssertion(elementName +" is not valid(not found on the PDF)");
            }
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to compare Pdf and Data for : "+elementName);
        }
    }

    public static void CompareValues(String value1, String value2,String elementName) {
        String v1= value1.toLowerCase().trim();
        String v2 =value2.toLowerCase().trim();
        try {
            if(!v1.contains(v2)){
                ExceptionHandler.HandleAssertion(elementName +" is invalidated (has an empty value)");
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to verify " + elementName );
        }
    }
    public static void CompareData(By object, String value, String elementName) {
        try {
            String text = "";

            if(!driver.findElements(object).isEmpty()) {
                text = driver.findElement(object).getText();
                if(!text.isEmpty()){
                    if(value.trim().isEmpty())
                    {
                        ExceptionHandler.HandleAssertion(elementName +" is invalidated (has an empty value)");
                    }
                }
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element " + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to verify " + elementName );
        }
    }

    public static void SwitchTabs() {
        try {
            Set<String> windows = driver.getWindowHandles();
            String sCurrentHandle = driver.getWindowHandle();
            for (String window:windows)
            {
                if(!sCurrentHandle.equalsIgnoreCase(window))
                {
                    driver.switchTo().window(window);
                }
            }
        } catch(Exception e) {
            ExceptionHandler.HandleException(e, "Unable to Switch Tabs");
        }
    }



    public static String getCurrentDate(String strFormat)
    {
        try{
            DateFormat dateFormat = new SimpleDateFormat(strFormat);
            Date dateObj = new Date();
            return dateFormat.format(dateObj);
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to get Current Date:" + strFormat);
            return null;
        }
    }

    public static void selectByVisibleText(By objLocator, String sVisibletext) throws Throwable {
        try {
            if (isElementVisible(objLocator, sVisibletext)) {

                Select s = new Select(driver.findElement(objLocator));
                s.selectByVisibleText(sVisibletext);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Select visible text" + sVisibletext);
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select visible text: " + sVisibletext);
        }
    }

    public static void selectByIndex(By objLocator, String sText) throws Throwable {
        try {

            if (isElementVisible(objLocator, sText)) {

                Select s = new Select(driver.findElement(objLocator));
                s.selectByValue(sText);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find" + sText);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select value text " + sText);
        }
    }

    public static boolean isElementEnabled(By objLocator) throws Throwable {
        boolean bflag=false;
        try {
            if (driver.findElement(objLocator).isEnabled()) {
                bflag=true;
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to check if element is enabled");
        }
        return bflag;
    }
    public static String GetScreenShot() throws Exception
    {
        String sScreenShotNameWithPath = null;

        try {
            Date oDate = new Date();
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = oSDF.format(oDate);

            File fScreenshot = ((TakesScreenshot) Base.driver).getScreenshotAs(OutputType.FILE);
            sScreenShotNameWithPath = System.getProperty("user.dir")+"\\WinDeedData\\Screenshots\\"+"Screenshot_" + sDate + ".png";
            FileUtils.copyFile(fScreenshot, new File(sScreenShotNameWithPath));
        } catch (Exception e) {
            ExceptionHandler.HandleScreenShotException(e, "Failed to get screen shot");
        }

        return sScreenShotNameWithPath;
    }

    public static List<WebElement> getElements(By Obj) throws Throwable{
        List<WebElement> webele=null;
        try {
            webele=driver.findElements(Obj);
        }  catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to getElement");
        }
        return webele;
    }

    public static void clearTextbox(By object,String elementName) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                driver.findElement(object).clear();
            } else ExceptionHandler.HandleAssertion("Unable to find Element");
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e,"Failed to clear text from " + elementName);
        }
    }

    public static void scrollToBottom() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(2000);
    }

    public static void clickOnElement(By locator, String fileName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        element.click();
        System.out.println("Clicked download button for file: " + fileName);

        boolean isDownloaded = waitForFileToDownload(fileName, 15);
        assertTrue("File was not downloaded successfully: " + fileName, isDownloaded);
    }

    public static boolean waitForFileToDownload(String fileName, int timeoutSeconds) throws InterruptedException {
        Path filePath = Paths.get(DOWNLOAD_DIR, fileName);
        File file = filePath.toFile();
        int waited = 0;

        while (waited < timeoutSeconds) {
            if (file.exists() && file.length() > 0) {
                System.out.println("File downloaded successfully: " + file.getAbsolutePath());
                return true;
            }
            Thread.sleep(1000);
            waited++;
        }

        System.out.println("File not found after waiting " + timeoutSeconds + " seconds");
        return false;
    }


    public static boolean productText(By object, String productName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(object));
            String elementText = element.getText() != null ? element.getText().trim() : "";
            return elementText.toLowerCase().contains(productName.toLowerCase());
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to validate product text for: " + productName);
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }



}


