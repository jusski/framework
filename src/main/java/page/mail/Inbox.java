package page.mail;

import java.time.Duration;
import java.time.Instant;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.NoArgsConstructor;
import page.Page;
import page.mail.Mail.InvalidMail;

@NoArgsConstructor
public class Inbox extends Page
{
    private String URL = "https://yopmail.com/en/wm";
    
    @FindBy(css = "#message")
    WebElement messageCountText;
    
    @FindBy(css = "iframe#ifmail")
    WebElement mailBodyFrame;
    
    @FindBy(css = "button#refresh")
    WebElement refreshButton;

    private String emailAddress;
    
    public Inbox(String emailAddress)
    {
        this.emailAddress = emailAddress;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    private Inbox refresh()
    {
        refreshButton.click();
        waitFor(() -> refreshButton.getAttribute("loading") == null);
        
        return this;
    }
    
    public Mail waitForEmailArrival(Duration timeout)
    {
        Instant endTime = Instant.now().plus(timeout);
        while(true)
        {
            refresh();
            if(!messageCountText.getText().equals("This inbox is empty"))
            {
                driver.switchTo().frame(mailBodyFrame);
                break;
            }
            if(Instant.now().isAfter(endTime))
            {
                return new InvalidMail();
            }
        }
        
        return new Mail();
    }
    
    public String getEmailAddress()
    {
        return emailAddress;
    }
    
  
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isValid && 
               driver.getCurrentUrl().startsWith(URL) &&
               refreshButton.isDisplayed();
    }
    
    private class InvalidInbox extends Inbox
    {
        boolean isValid = false;
        
        @Override
        public boolean isPageStateCorrect()
        {
            return isValid;
        }
        
    }
}
