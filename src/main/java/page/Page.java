package page;

import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import driver.Driver;

public abstract class Page 
{
    protected int TIME_OUT_IN_SECONDS = 10;
    protected Duration TIMEOUT = Duration.ofSeconds(TIME_OUT_IN_SECONDS);
    protected WebDriver driver = Driver.getInstance();
    
    public abstract boolean isPageStateCorrect();
   
    public FluentWait<WebDriver> sleep()
    {
        return sleep(TIMEOUT);
    }
    
    public FluentWait<WebDriver> sleep(Duration timeout)
    {
       return new FluentWait<WebDriver>(driver)
        .withTimeout(timeout)
        .pollingEvery(Duration.ofMillis(500))
        .ignoring(NoSuchElementException.class);
    }
    
    public boolean isUrlBeginningWith(String url)
    {
        return expectedCondition((driver) -> driver.getCurrentUrl().startsWith(url));
    }
    
    public  ExpectedCondition<Boolean> urlChanges(String currentUrl)
    {
        return ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl));
    }
    
    public boolean isDisplayed(WebElement element)
    {
        try
        {
            return element.isDisplayed();
        }
        catch(TimeoutException | NoSuchElementException e)
        {
            
        }
        return false;
    }
    
    public  boolean expectedCondition(ExpectedCondition<Boolean> expectedCondition)
    {
        try
        {
            return sleep().until(expectedCondition);
        }
        catch(TimeoutException | NoSuchElementException e)
        {
            
        }
        return false;
    }
    
//    @Override
//    protected void isLoaded() throws Error
//    {
//        Assert.assertTrue(isPageStateCorrect());
//    }
//    
    
}
