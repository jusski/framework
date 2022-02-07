package experiments;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
//@Test(groups = "smoke")
public class AbstractGroupTest
{
    @BeforeSuite(alwaysRun = true)
    public void beforeAbstractSuite1()
    {
        log.trace("Before abstract suite.");
    }

    @BeforeGroups
    public void beforeAbstractGroup1()
    {
        log.trace("Before abstract group.");
    }

    @BeforeClass
    public void beforeAbstractClass1()
    {
        log.trace("Before abstract class.");
    }

    @BeforeTest
    public void beforeAbstractTest1()
    {
        log.trace("Before abstract group.");
    }

    @BeforeMethod
    public void beforeAbstractMethod1()
    {
        log.trace("Before abstract method.");
    }
}
