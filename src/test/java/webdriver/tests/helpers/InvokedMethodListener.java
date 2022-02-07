package webdriver.tests.helpers;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import lombok.extern.log4j.Log4j2;
import webdriver.driver.Driver;

@Log4j2
public class InvokedMethodListener implements IInvokedMethodListener
{
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context)
    {
        Integer driverId = (Integer)context.getAttribute("driverId");
        if(driverId != null)
        {
            log.trace("DriverId = {}", driverId);
            Driver.setThreadLocalDriver(driverId);
        }
        else
        {
            log.warn("could not find driverId in context.");
        }
    }
}
