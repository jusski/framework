package webdriver.page.google.cloud;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;

@Log4j2
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
        log.trace("Searching for {}", string);
        clickObscured(searchButton); // NOTE obscuring depends on window size
       
        searchInput.sendKeys(string); 
        searchForm.submit();
        
        return new SearchResultsPage(windowHandle);
    }
    
    public MainPage open()
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
