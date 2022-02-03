

import java.util.Arrays;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import tests.helpers.InvokedMethodListener;

//@Listeners({InvokedMethodListener.class})
public class NewTest
{
    int a;
    
    public NewTest(int a)
    {
        this.a = a;
    }
    
    @DataProvider(name = "provider")
    public Iterator<Integer> createData()
    {
        return Arrays.asList(1,2,3,4).iterator();
    }
    
    @Factory
    public static Object[] testFactory()
    {
        return Arrays.asList(new NewTest(1), new NewTest(345)).toArray();
    }
    
    @BeforeSuite()
    public void beforeSuite(ITestContext context)
    {
        context.setAttribute("liaw", "bam");
        System.out.println(Arrays.toString(context.getAllTestMethods()));
        System.out.println("BeforeSuite " + Thread.currentThread().getId());
    }
   
    public static class InvokedMethodListener implements IInvokedMethodListener
    {
      
        @Override
        public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context)
        {
            System.out.println("before invocation " + Thread.currentThread().getId());
        }

    }
    
    @Test(description = "tam tam tam")
    public void f(ITestContext context)
    {
        System.out.println("some test " + Thread.currentThread().getId());
        System.out.println("***** " + a + " ********");
        Assert.assertTrue(true);
    }
   
}
