package experiments;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Test(groups = "smoke")
public class GroupTest extends AbstractGroupTest
{

    @BeforeSuite
    public void beforeSuite1()
    {
        log.trace("Before suite.");
    }
    
    @BeforeGroups
    public void beforeGroup1()
    {
        log.trace("Before group.");
    }


    @BeforeClass
    public void beforeClass1()
    {
        log.trace("Before class.");
    }

    @BeforeTest
    public void beforeTest1()
    {
        log.trace("Before group.");
    }
    

    @BeforeMethod
    public void beforeMethod1()
    {
        log.trace("Before method.");
    }
    
    @Test
    public void test1()
    {
        log.info("Running important test.");
    }
    
}
