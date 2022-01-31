package tests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import driver.Driver;
import tests.helpers.ScreenshotOnFailedBuildListener;

@Listeners({ScreenshotOnFailedBuildListener.class})
public class AbstractTest
{
    @BeforeMethod
    public void beforeMethod()
    {
        Driver.newInstance();
    }
    
    @AfterMethod(alwaysRun = true)
    public void afterMethod()
    {
        Driver.shutdown();
    }

}
