import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import tests.SmokeTest;

public class Runner
{
    public static void main()
    {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { SmokeTest.class });
        testng.addListener(tla);
        testng.run();
        
    }

}