package driver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomWebDriver implements WebDriver
{
    private AtomicInteger frameId = new AtomicInteger(0);
    private final WebDriver driver;

    public CustomWebDriver(WebDriver driver)
    {
        this.driver = driver;
    }

    public int getFrameId()
    {
        return frameId.get();
    }
    
    @Override
    public void get(String url)
    {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl()
    {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle()
    {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by)
    {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by)
    {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource()
    {
        return driver.getPageSource();
    }

    @Override
    public void close()
    {
        driver.close();
    }

    @Override
    public void quit()
    {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles()
    {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle()
    {
       return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo()
    {
        frameId.incrementAndGet();
        return driver.switchTo();
    }

    @Override
    public Navigation navigate()
    {
        return driver.navigate();
    }

    @Override
    public Options manage()
    {
        return driver.manage();
    }

    public void executeScript(String string, WebElement element)
    {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor)driver;
        javaScriptExecutor.executeScript(string, element);
    }

  

}
