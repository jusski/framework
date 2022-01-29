package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class EmailEstimate extends Page
{

    private final String URL = "https://cloud.google.com/products/calculator#id";
    @FindBy(css = "form[name='emailForm']")
    WebElement emailForm;
    
    @FindBy(css = "input[ng-model='emailQuote.user.email'])")
    WebElement emailInput;
    
    @FindBy(css = "form[name='emailForm'] button.cpc-button")
    WebElement sendEmailButton;
    
    public EmailEstimate()
    {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return driver.getCurrentUrl().startsWith(URL) &&
               emailForm.isDisplayed();
    }
}
