package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends Page
{
    private final String URL = "https://cloud.google.com/";
    
    @FindBy(css = "form > div > button.devsite-search-button")
    WebElement searchButton;
    
    @FindBy(css = "form.devsite-search-form")
    WebElement searchForm;
    
    @FindBy(css = "input.devsite-search-field")
    WebElement searchInput;
    
    public MainPage()
    {
        PageFactory.initElements(driver, this);
    }
    
    public SearchResultsPage invokeSearch(String string) throws InterruptedException
    {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].click();", searchButton);
        
        searchInput.sendKeys("Google Cloud Platform Pricing Calculator"); 
        searchForm.submit();
        
        return new SearchResultsPage(string).open();
    }
    
    public MainPage open()
    {
        driver.get(URL);
       
        return this;
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isUrlBeginningWith(URL);
    }
}
