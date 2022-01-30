package page.google.cloud.calculator.compute.engine;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.NoArgsConstructor;
import page.Page;

@NoArgsConstructor
public class EmailEstimateForm extends Page
{
    String URL = "https://cloud.google.com/products/calculator#id";
    ComputeEngine parent;
    
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
    
    private int frameId;
    
    public EmailEstimateForm(boolean isValid, ComputeEngine parent)
    {
        this.isValid = isValid;
        this.parent = parent;
        this.frameId = driver.getFrameId();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public EmailEstimateForm enterEmailAddress(String email)
    {
        if(isPageStateCorrect())
        {
            scrollIntoViewAndClick(emailInput);
            emailInput.sendKeys(email);
        }
        else
        {
            return new InvalidEmailEstimateForm();
        }
        
        return this;
    }
    
    public ComputeEngine clickSendButton()
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
    public boolean isPageStateCorrect()
    {
        return isValid &&
               driver.getCurrentUrl().startsWith(URL) &&
               ((frameId == driver.getFrameId()) || changeToCorrectFrame()) &&
               emailForm.isDisplayed();
    }
    
    private boolean changeToCorrectFrame()
    {
        try
        {
            driver.switchTo().frame(outerFrame).switchTo().frame(mainFrame); 
            frameId = driver.getFrameId();
        }
        catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.warning("Tried to switch frames and it failed. " + e.getMessage());
            return false;
        }

        return true;
    }

    static class InvalidEmailEstimateForm extends EmailEstimateForm
    {
        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    }
}
