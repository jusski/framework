package page.google.cloud;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    
    public MainPage()
    {
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
        get(URL);
        return this;
    }
}
