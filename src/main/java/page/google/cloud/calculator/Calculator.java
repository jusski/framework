package page.google.cloud.calculator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.NoArgsConstructor;
import page.Page;
import page.google.cloud.calculator.compute.engine.ComputeEngine;

@NoArgsConstructor
public class Calculator extends Page
{
    private final String URL = "https://cloud.google.com/products/calculator";
    
    @FindBy(css = "md-tab-item > div[title='Compute Engine']")
    WebElement computeEngine;
    
    @FindBy(css = "devsite-iframe > iframe")
    WebElement outerFrame;
    
    @FindBy(css = "#myFrame")
    WebElement mainFrame;

    private String windowHandle;
    
    public Calculator(String windowHandle)
    {
        this.windowHandle = driver.getWindowHandle();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public ComputeEngine invokeComputeEngine()
    {
        driver.switchTo().frame(outerFrame);
        driver.switchTo().frame(mainFrame);
        computeEngine.click();
        
        return new ComputeEngine(windowHandle);
    }
    
    public Calculator open()
    {
        driver.get(URL);
        
        return this;
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isValid &&
                ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
                driver.getCurrentUrl().startsWith(URL);
    }

}
