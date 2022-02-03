package webdriver.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromiumDriverManager;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Driver
{
    private static ThreadLocal<CustomWebDriver> driver = new ThreadLocal<>();
   
    private static Map<Integer, CustomWebDriver> driversMap = new HashMap<>();
   
    /**
     * Returns singleton threadlocal WebDriver instance
     */
    public static CustomWebDriver getInstance()
    {
        CustomWebDriver webdriver = driver.get();
        if(webdriver == null)
        {
           log.error("Thread local webdriver was null. It should not happen.");
           webdriver = newInstance();
        }
        
        return webdriver;
    }
    
    public static void setThreadLocalDriver(int id)
    {
        CustomWebDriver webdriver = driversMap.get(id);
        if(webdriver != null)
        {
            driver.set(webdriver);
        }
        else
        {
            throw new RuntimeException("Driver instance for id = " + id + " was not found.");
        }
    }
    
    private static CustomWebDriver newInstance()
    {
        CustomWebDriver webdriver;
        switch(System.getProperty("browser", "chrome"))
        {
            case "chrome":
            {
                log.trace("Creating chrome webdriver.");
                WebDriverManager.chromiumdriver().setup();
                webdriver = new CustomWebDriver(new ChromeDriver());
            } break;
            case "firefox":
            {
                log.trace("Creating firefox webdriver.");
                WebDriverManager.firefoxdriver().setup();
                webdriver = new CustomWebDriver(new FirefoxDriver());
            } break;
            default:
            {
                throw new RuntimeException("Support for this driver is not implemented.");
            }
        }

        return webdriver;
    }
    
    
    /**
     * Creates new WebDriver instance or returns old instance with same id.
     */
    public static CustomWebDriver newInstance(int id)
    {
        return driversMap.computeIfAbsent(id, k -> newInstance());
    }
    
    
    /**
     * Calls driver.quit on webdriver instance with this id.
     */
    public static void shutdown(int id)
    {
        driversMap.computeIfPresent(id, (k, v) -> {v.quit(); return null;} );
    }

}
