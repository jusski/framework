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
public abstract class AbstractTest
{
    private static final AtomicInteger classInstanceId = new AtomicInteger(0);
    private final Integer id;
    
    public AbstractTest()
    {
        //We can use also System.identityHashCode(this) for id;
        id = classInstanceId.getAndIncrement();
        log.trace("classInstanceId = {}", id);
    }
   
    @BeforeClass(alwaysRun = true)
    public void beforeSuite(ITestContext context)
    {
        log.trace("set context driverId ({})", id);
        context.getSuite().setAttribute("driverId", id);
        context.setAttribute("driverId", id);
        Driver.newInstance(id);
    }
    
    @AfterClass(alwaysRun = true)
    public void afterSuite(ITestContext context)
    {
        Driver.shutdown(id);
        context.removeAttribute("driverId");
        log.trace("removed context driverId ({})", id);
    }
    
    
}
