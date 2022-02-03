package webdriver.page.mail;

import java.util.UUID;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.google.common.base.Supplier;

import webdriver.page.Page;

public class MailProviderPage extends Page
{
    String URL = "https://yopmail.com/en/";
  
    @FindBy(css = "input#login.ycptinput")
    WebElement emailAddressInput;
    
    @FindBy(css = "#refreshbut > button")
    WebElement inboxButton;
    
    @FindBy(css = "#accept")
    WebElement cookiesBannerAcceptButton;
    
    public String emailAddress;

    public MailProviderPage()
    {
        this(true);
    }
    
    public MailProviderPage(boolean switchToNewTab)
    {
        if(switchToNewTab) driver.switchTo().newWindow(WindowType.TAB);

        windowHandle = driver.getWindowHandle();
        emailAddress = UUID.randomUUID().toString().substring(0, 15);
      
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }

    public MailProviderPage open()
    {
        navigateTo(URL);
        cookiesBannerAcceptButton.click();
        emailAddressInput.click();
        emailAddressInput.sendKeys(emailAddress);
        return this;
    }

    public String getEmailAddress()
    {
        return emailAddress + "@YOPmail.com";
    }

    public InboxPage openInbox()
    {
        String currentUrl = driver.getCurrentUrl();
        inboxButton.click();
        waitFor(() -> !driver.getCurrentUrl().equals(currentUrl));
        
        return new InboxPage(getEmailAddress());
    }
    
    @Override
    public boolean isPageAttributesCorrect()
    {
        return inboxButton.isDisplayed();
    }
    
}
