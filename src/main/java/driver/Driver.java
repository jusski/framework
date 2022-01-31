package driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver
{
    private static CustomWebDriver driver;

    
    /**
     * Returns singleton WebDriver instance
     */
    public static CustomWebDriver getInstance()
    {
        if(driver == null)
        {
            switch(System.getProperty("driver", "chrome"))
            {
                case "chrome":
                {
                    WebDriverManager.chromedriver().setup();
                    driver = new CustomWebDriver(new ChromeDriver());
                } break;
                case "firefox":
                {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new CustomWebDriver(new FirefoxDriver());
                } break;
                default:
                {
                    throw new RuntimeException("Support for this driver is not implemented.");
                }
            }
        }

        return driver;
    }
    
    /**
     * Creates new WebDriver instance
     */
    public static CustomWebDriver newInstance()
    {
        shutdown();
        return getInstance();
    }
    
    public static void shutdown()
    {
        if(driver != null)
        {
            driver.quit();
            driver = null;
        }
    }

}
