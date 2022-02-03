package webdriver.tests.helpers;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.google.common.io.Files;

import lombok.SneakyThrows;
import webdriver.driver.Driver;
import webdriver.page.util.IO;

public class ScreenshotOnFailedBuildListener implements ITestListener
{
    @Override
    public void onTestFailure(ITestResult result)
    {
        takeScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        if(result.wasRetried())
        {
            takeScreenshot(result);
        }
    }
   
    @SneakyThrows
    private void takeScreenshot(ITestResult result)
    {
        File screenshot = Driver.getInstance().getScreenshotAs(OutputType.FILE);
        String filename = String.format("target/screenshots/%s.png", result.getMethod().getQualifiedName());
        Files.copy(screenshot, IO.createFile(new File(filename)));
    }

}
