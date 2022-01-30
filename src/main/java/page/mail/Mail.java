package page.mail;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import page.Page;

public class Mail extends Page
{
    @FindBy(css = "#mail")
    WebElement mailBody;
    
    public Mail()
    {
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
    
    static class InvalidMail extends Mail
    {
        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    }
}
