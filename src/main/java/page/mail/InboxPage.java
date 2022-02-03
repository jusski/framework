package page.mail;

import java.time.Duration;
import java.time.Instant;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import page.Page;
import page.mail.MailPage.InvalidMail;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InboxPage extends Page
{
    private String URL = "https://yopmail.com/en/wm";
    
    @FindBy(css = "#message")
    WebElement messageCountText;
    
    @FindBy(css = "iframe#ifmail")
    WebElement mailBodyFrame;
    
    @FindBy(css = "button#refresh")
    WebElement refreshButton;

    private String emailAddress;
    
    public InboxPage(String emailAddress)
    {
        this.emailAddress = emailAddress;

        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    private InboxPage refreshInbox()
    {
        refreshButton.click();
        waitFor(() -> refreshButton.getAttribute("loading") == null);
        
        return this;
    }
    
    public MailPage waitForEmailArrival(Duration timeout)
    {
        if(isPageStateCorrect())
        {
            Instant endTime = Instant.now().plus(timeout);
            while(true)
            {
                refreshInbox();
                if(!messageCountText.getText().equals("This inbox is empty"))
                {
                    driver.switchTo().frame(mailBodyFrame);
                    break;
                }
                if(Instant.now().isAfter(endTime))
                {
                    log.error("Timeout.");
                    return new InvalidMail();
                }
            }
            
            return new MailPage();
        }
  
       return new InvalidMail();  
    }
    
    public String getEmailAddress()
    {
        return emailAddress;
    }
    
    @Override
    public boolean isPageAttributesCorrect()
    {
        return refreshButton.isDisplayed();
    }

    private class InvalidInbox extends InboxPage
    {
        boolean isValid = false;
        
        public  InvalidInbox()
        {
            log.error("Invalid Inbox was created");
        }
        
        @Override
        public boolean isPageStateCorrect()
        {
            return isValid;
        }
        
    }
}
