package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Calculator extends Page
{
    private final String URL = "https://cloud.google.com/products/calculator";
    
    @FindBy(css = "md-tab-item > div[title='Compute Engine']")
    WebElement computeEngine;
    
    
    public Calculator()
    {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public ComputeEngine invokeComputeEngine()
    {
        computeEngine.click();
        
        return new ComputeEngine();
    }
    
    public Calculator open()
    {
        driver.get(URL);
        driver.switchTo().frame(driver.findElement(By.cssSelector("devsite-iframe > iframe")));
        driver.switchTo().frame("myFrame");
        return this;
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return driver.getCurrentUrl().startsWith(URL);
    }

}
