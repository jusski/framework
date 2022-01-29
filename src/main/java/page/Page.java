package page;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import driver.Driver;

abstract class Page 
{
    protected int TIME_OUT_IN_SECONDS = 10;
    protected Duration TIMEOUT = Duration.ofSeconds(TIME_OUT_IN_SECONDS);
    protected WebDriver driver = Driver.getInstance();
    
    public abstract boolean isPageStateCorrect();
   
    protected FluentWait<WebDriver> sleep()
    {
        return sleep(TIMEOUT);
    }
    
    protected FluentWait<WebDriver> sleep(Duration timeout)
    {
       return new FluentWait<WebDriver>(driver)
        .withTimeout(timeout)
        .pollingEvery(Duration.ofMillis(200))
        .ignoring(NoSuchElementException.class);
    }
    
    protected boolean isUrlBeginningWith(String url)
    {
        return expectedCondition((driver) -> driver.getCurrentUrl().startsWith(url));
    }
    
    protected  ExpectedCondition<Boolean> urlChanges(String currentUrl)
    {
        return ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl));
    }
    
    protected void clickObscured(WebElement element)
    {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].click();", element);
    }
    
    protected void scrollIntoViewAndClick(WebElement element)
    {
        scrollIntoView(element);
        element.click();
    }
    
    protected void scrollIntoView(WebElement element)
    {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
    
    protected void scrollIntoViewTop(WebElement element)
    {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].scrollIntoView();", element);
    }
    
    protected boolean isDisplayed(WebElement element)
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
    
    protected boolean isEnabled(WebElement element)
    {
        try
        {
            return element.isEnabled();
        }
        catch(TimeoutException | NoSuchElementException e)
        {
            
        }
        return false;
    }
    
    protected  boolean expectedCondition(ExpectedCondition<Boolean> expectedCondition)
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
