package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends Page
{
    private final String URL = "https://cloud.google.com/";
    
    @FindBy(css = "form > div > button.devsite-search-button")
    WebElement searchButton;
    
    @FindBy(css = ".devsite-suggest-wrapper")
    WebElement searchPopup;
    
    @FindBy(css = "input.devsite-search-field")
    WebElement searchInput;
    
    public MainPage()
    {
        PageFactory.initElements(driver, this);
    }
    
    public SearchResultsPage invokeSearch(String string)
    {
        String currentUrl = driver.getCurrentUrl();
       
        searchButton.click();
        sleep().until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys("Google Cloud Platform Pricing Calculator", Keys.ENTER); 
        sleep().until(urlChanges(currentUrl));
        
        return new SearchResultsPage().open();
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
