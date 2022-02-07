import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import webdriver.tests.GoogleSearch;

public class Runner
{
    public static void main()
    {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { GoogleSearch.class });
        testng.addListener(tla);
        testng.run();
        
    }

}