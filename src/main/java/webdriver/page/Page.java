package webdriver.page;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.management.openmbean.InvalidOpenTypeException;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import lombok.extern.log4j.Log4j2;
import webdriver.driver.CustomWebDriver;
import webdriver.driver.Driver;

@Log4j2
public class Page 
{
    private static final int POLLING_DURATION_MILISECONDS = 100;
    protected int TIME_OUT_IN_SECONDS = 15;
    protected Duration TIMEOUT = Duration.ofSeconds(TIME_OUT_IN_SECONDS);
    protected CustomWebDriver driver = Driver.getInstance();
    protected FluentWait<WebDriver> fluentWait = sleep();
    protected String windowHandle = driver.getWindowHandle();
  
    protected int frameId = driver.getFrameId();
    public boolean isValid = true;
    
    private String URL = driver.getCurrentUrl();
    
       
    protected void navigateTo(String url)
    {
        log.trace("Opening {} page", url);
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        this.URL = url;
    }
    
    public boolean isPageStateCorrect()
    {
        return isValid && 
               isBrowserWindowCorrect() &&
               driver.getCurrentUrl().startsWith(URL) &&
               isPageAttributesCorrect();
    }
    
    protected boolean isPageAttributesCorrect()
    {
        return true;
    }

    protected boolean isIFrameCorrect()
    {
        return ((driver.getFrameId() == frameId) || changeToCorrectFrame());
    }

    protected boolean changeToCorrectFrame()
    {
        throw new InvalidArgumentException("This method should be overrided.");
    }
    
    protected boolean isBrowserWindowCorrect()
    {
        if(driver.getWindowHandle().equals(windowHandle))
        {
            return true;
        }
        return changeToCorrectWindow(windowHandle);
    }
    
    private boolean changeToCorrectWindow(String windowHandle)
    {
        try
        {
            driver.switchTo().window(windowHandle);
        }
        catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.error("Tried to switch windows and it failed. ", e);
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
        .pollingEvery(Duration.ofMillis(POLLING_DURATION_MILISECONDS))
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
    
    protected WebElement clickObscured(WebElement element)
    {
        if(isValid)
        {
            driver.executeScript("arguments[0].click();", element);
            log.warn("Clicked obscured {}", element);
        }
        
        return element;
    }
 
    protected void scrollIntoViewAndClick(WebElement element)
    {
        if(isValid)
        {
            scrollIntoView(element);
            if(element.isDisplayed() && element.isEnabled())
            {
                log.trace("Clicking {}", element);
                element.click();
            }
            else
            {
                isValid = false;
                log.error("Element: {} is not displayed or enabled", element);
            }
        }
    }
    
    protected WebElement scrollIntoView(WebElement element)
    {
        if(isValid)
        {   
            log.trace("Scrolling to {}", element);
            driver.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        }
        
        return element;
    }
    
    protected WebElement scrollIntoViewTop(WebElement element)
    {
        if(isValid)
        {
            log.trace("Scrolling to {}", element);
            driver.executeScript("arguments[0].scrollIntoView();", element);
        }
        
        return element;
    }
    
    protected boolean isDisplayed(WebElement element)
    {
        try
        {
            return element.isDisplayed();
        }
        catch(TimeoutException | NoSuchElementException e)
        {
            log.trace(e.getMessage());
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
            log.trace(e.getLocalizedMessage());
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
            log.trace(e.getLocalizedMessage());
        }
        return false;
    }
}
