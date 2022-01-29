package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver
{
    private static WebDriver driver;

    public static WebDriver getInstance()
    {
        if(driver == null)
        {
            switch(System.getProperty("driver", "chrome"))
            {
                case "chrome":
                {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                } break;
                case "firefox":
                {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                } break;
                default:
                {
                    throw new RuntimeException("Support for this driver is not implemented.");
                }
            }
        }

        return driver;
    }

}
