package webdriver.page.google.cloud.calculator.compute.engine;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;

@Log4j2
public class EmailEstimateFormPage extends Page
{
    String URL = "https://cloud.google.com/products/calculator/#id";
    ComputeEnginePage parent;
    
    @FindBy(css = "form[name='emailForm']")
    WebElement emailForm;
    
    @FindBy(css = "input[ng-model='emailQuote.user.email']")
    WebElement emailInput;
    
    @FindBy(css = "form[name='emailForm'] button.cpc-button")
    WebElement sendEmailButton;
   
    @FindBy(css = "devsite-iframe > iframe")
    WebElement outerFrame;
    
    @FindBy(css = "#myFrame")
    WebElement mainFrame;
    
    
    public EmailEstimateFormPage(boolean isValid, ComputeEnginePage parent)
    {
        this.isValid = isValid;
        this.parent = parent;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public EmailEstimateFormPage enterEmailAddress(String email)
    {
        if(isPageStateCorrect())
        {
            scrollIntoViewAndClick(emailInput);
            emailInput.sendKeys(email);
        }
        else
        {
            return new InvalidEmailEstimateForm(false, parent);
        }
        
        return this;
    }
    
    public ComputeEnginePage clickSendButton()
    {
        if(isPageStateCorrect())
        {
            waitFor(() -> sendEmailButton.isEnabled());
            sendEmailButton.click();
        }
        else
        {
            parent.isValid = false;
        }
        
        return parent;
    }
    
    @Override
    public boolean isPageAttributesCorrect()
    {
        return isIFrameCorrect() &&
               driver.getCurrentUrl().startsWith(URL) &&
               emailForm.isDisplayed();
    }
    
    @Override
    protected boolean changeToCorrectFrame()
    {
        try
        {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(outerFrame).switchTo().frame(mainFrame); 
            frameId = driver.getFrameId();
        }
        catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.error("Tried to switch frames and it failed. ", e);
            return false;
        }

        return true;
    }

    static class InvalidEmailEstimateForm extends EmailEstimateFormPage
    {
        public InvalidEmailEstimateForm(boolean isValid, ComputeEnginePage parent)
        {
            super(false, parent);
            log.error("Invalid EmailEstimateForm was created");
        }

        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    }
}
