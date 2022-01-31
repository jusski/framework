package tests.helpers;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.google.common.io.Files;

import driver.Driver;
import lombok.SneakyThrows;
import page.util.IO;

public class ScreenshotOnFailedBuildListener implements ITestListener
{
    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result)
    {
        File screenshot = Driver.getInstance().getScreenshotAs(OutputType.FILE);
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss").format(LocalDateTime.now());
        String filename = "target/screenshots/" + date + result.getMethod() + ".png";
        Files.copy(screenshot, IO.createFile(new File(filename)));
    }

}
