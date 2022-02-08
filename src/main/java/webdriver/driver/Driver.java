package webdriver.driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
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
            log.trace("Setting thread local driver to id({})", id);
            driver.set(webdriver);
        }
        else
        {
            log.error("Driver instance for id = " + id + " was not found.");
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
                ChromeOptions chromeOptions;
                log.trace("Creating chrome webdriver.");
                       
                String binary = System.getProperty("browser_binary");
                String version = System.getProperty("browser_version");
                if(binary != null && version != null)
                {
                    log.trace("Setting chrome binary to {}", binary);
                    log.trace("Setting chrome version to {}", version);
                    WebDriverManager.chromiumdriver().driverVersion(version).setup(); 
                    
                    chromeOptions = new ChromeOptions()
                            .setBinary(binary)
                            .setHeadless(false);
                    
                }
                else
                {
                    WebDriverManager.chromiumdriver().driverVersion("97.0.4692.71").setup(); 
                    
                    chromeOptions = new ChromeOptions()
                            .setBinary("C:\\Users\\R61\\AppData\\Local\\Chromium\\Application\\chrome.exe")
                            .setHeadless(false);
                }
                
                webdriver = new CustomWebDriver(new ChromeDriver(chromeOptions));
                
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
