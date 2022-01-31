package page;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import driver.CustomWebDriver;
import driver.Driver;

public class Page 
{
    protected static Logger log = Logger.getLogger(Page.class.getName()); 
    
    protected int TIME_OUT_IN_SECONDS = 15;
    protected Duration TIMEOUT = Duration.ofSeconds(TIME_OUT_IN_SECONDS);
    protected CustomWebDriver driver = Driver.getInstance();
    protected FluentWait<WebDriver> fluentWait = sleep();
    public boolean isValid = true;
    
    public boolean isPageStateCorrect()
    {
        return isValid;
    }
   
    protected boolean changeToCorrectWindow(String windowHandle)
    {
        try
        {
            driver.switchTo().window(windowHandle);
        }
        catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.warning("Tried to switch windows and it failed. " + e.getMessage());
            return false;
        }

        return true;
    }
    
    protected FluentWait<WebDriver> sleep()
    {
        return sleep(TIMEOUT);
    }
    
    protected void waitFor(Supplier<Boolean> supplier) throws TimeoutException
    {
        fluentWait.until(ignored -> supplier.get());
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
        if(isValid)
        {
            driver.executeScript("arguments[0].click();", element);
        }
    }
    
    protected void scrollIntoViewAndClick(WebElement element)
    {
        if(isValid)
        {
            scrollIntoView(element);
            if(element.isDisplayed() && element.isEnabled())
            {
                element.click();
            }
            else
            {
                log.warning(String.format("Element: %s is not displayed or enabled", element));
            }
        }
    }
    
    protected void scrollIntoView(WebElement element)
    {
        if(isValid)
        {
            driver.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        }
    }
    
    protected void scrollIntoViewTop(WebElement element)
    {
        if(isValid)
        {
            driver.executeScript("arguments[0].scrollIntoView();", element);
        }
    }
    
    protected boolean isDisplayed(WebElement element)
    {
        try
        {
            return element.isDisplayed();
        }
        catch(TimeoutException | NoSuchElementException e)
        {
            log.fine(e.getLocalizedMessage());
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
            log.fine(e.getLocalizedMessage());
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
            log.fine(e.getLocalizedMessage());
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
