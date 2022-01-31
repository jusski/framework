package page.mail;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.NoArgsConstructor;
import page.Page;

@NoArgsConstructor
public class Mail extends Page
{
    private String URL = "https://yopmail.com/en/wm";
    
    @FindBy(css = "#mail")
    WebElement mailBody;
   
    private String windowHandle;
    
    public Mail(String windowHandle)
    {
       this.windowHandle = windowHandle;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }
    
    public String getMailBody()
    {
        if(isPageStateCorrect())
        {
            try
            {
                return mailBody.getText();
            }
            catch (TimeoutException e)
            {
                log.warning(e.getMessage());
            }
        }
        
        return "";
    }
    
    @Override
    public boolean isPageStateCorrect()
    {
        return isValid && 
               ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
               driver.getCurrentUrl().startsWith(URL) &&
               mailBody.isDisplayed();
    }

   
    @NoArgsConstructor
    static class InvalidMail extends Mail
    {
        public InvalidMail(String windowHandle)
        {
            super(windowHandle);
        }

        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    }
}
