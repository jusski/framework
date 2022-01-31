package page.google.cloud;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import page.Page;

public class MainPage extends Page
{
    private final String URL = "https://cloud.google.com/";
    
    @FindBy(css = "form > div > button.devsite-search-button")
    WebElement searchButton;
    
    @FindBy(css = "form.devsite-search-form")
    WebElement searchForm;
    
    @FindBy(css = "input.devsite-search-field")
    WebElement searchInput;

    private String windowHandle;
    
    public MainPage()
    {
        this.windowHandle = driver.getWindowHandle();
        PageFactory.initElements(driver, this);
    }
    
    public SearchResultsPage invokeSearch(String string) 
    {
        clickObscured(searchButton); // NOTE obscuring depends on window size
       
        searchInput.sendKeys("Google Cloud Platform Pricing Calculator"); 
        searchForm.submit();
        
        return new SearchResultsPage(windowHandle);
    }
    
    public MainPage open()
    {
        driver.get(URL);
       
        return this;
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isValid &&
               ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
               isUrlBeginningWith(URL);
    }
}
