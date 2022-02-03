package webdriver.tests;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import lombok.extern.log4j.Log4j2;
import webdriver.driver.Driver;
import webdriver.tests.helpers.InvokedMethodListener;
import webdriver.tests.helpers.ScreenshotOnFailedBuildListener;

@Log4j2
@Listeners({ScreenshotOnFailedBuildListener.class, InvokedMethodListener.class})
public class AbstractTest
{
    private static final AtomicInteger classInstanceId = new AtomicInteger(0);
    private final Integer id;
    
    public AbstractTest()
    {
        //We can use also System.identityHashCode(this) for id;
        id = classInstanceId.getAndIncrement();
    }
   
    @BeforeClass
    public void beforeClass(ITestContext context)
    {
        Driver.newInstance(id);
        context.setAttribute("driverId", id);
    }
    
    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext context)
    {
        Driver.shutdown(id);
        context.removeAttribute("driverId");
    }

}
