package webdriver.page.google.cloud.calculator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;
import webdriver.page.google.cloud.calculator.compute.engine.ComputeEnginePage;

@Log4j2
public class CalculatorPage extends Page
{
    private final String URL = "https://cloud.google.com/products/calculator";
    
    @FindBy(css = "md-tab-item > div[title='Compute Engine']")
    WebElement computeEngine;
    
    @FindBy(css = "devsite-iframe > iframe")
    WebElement outerFrame;
    
    @FindBy(css = "#myFrame")
    WebElement mainFrame;
    
    @FindBy(css = "button.devsite-snackbar-action")
    WebElement cookieButton;

    public CalculatorPage() 
    {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public ComputeEnginePage invokeComputeEngine()
    {
        if(isPageStateCorrect())
        {
            cookieButton.click();
            driver.switchTo().frame(outerFrame);
            driver.switchTo().frame(mainFrame);
            computeEngine.click();

            return new ComputeEnginePage();
        }
        
        return null;
    }
    
    public CalculatorPage open()
    {
        newTabOpenURL(URL);
       
        return this;
    }
    
    @Override
    protected boolean isPageAttributesCorrect()
    {
        return driver.getCurrentUrl().startsWith(URL);
    }

}
