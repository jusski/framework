package page.mail;

import java.util.UUID;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import page.Page;

public class MailProvider extends Page
{
    private String URL = "https://yopmail.com/en/";
    
    @FindBy(css = "input#login.ycptinput")
    WebElement emailAddressInput;
    
    @FindBy(css = "#refreshbut > button")
    WebElement inboxButton;
    
    @FindBy(css = "#accept")
    WebElement cookiesBannerAcceptButton;
    
    public String emailAddress;

    private String windowHandle;

    public MailProvider()
    {
        this.windowHandle = driver.getWindowHandle();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
        emailAddress = UUID.randomUUID().toString().substring(0, 15);
    }

    public MailProvider open()
    {
        driver.get(URL);
        cookiesBannerAcceptButton.click();
        emailAddressInput.click();
        emailAddressInput.sendKeys(emailAddress);
        return this;
    }

    public String getEmailAddress()
    {
        return emailAddress + "@YOPmail.com";
    }

    public Inbox openInbox()
    {
        String currentUrl = driver.getCurrentUrl();
        inboxButton.click();
        waitFor(() -> !driver.getCurrentUrl().equals(currentUrl));
        
        return new Inbox(getEmailAddress(), windowHandle);
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isValid && 
               ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
               driver.getCurrentUrl().startsWith(URL) &&
               inboxButton.isDisplayed();
    }
    
}
