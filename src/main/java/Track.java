import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import lombok.extern.log4j.Log4j2;
import tests.SmokeTest;



@Log4j2
public class Track
{
    public static void main(String[] args)
    {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { SmokeTest.class });
        testng.addListener(tla);
        testng.run();
        
        
    }

}
