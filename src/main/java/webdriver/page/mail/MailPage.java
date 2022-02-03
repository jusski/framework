package webdriver.page.mail;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;
import webdriver.page.util.Exceptions;

@Log4j2
public class MailPage extends Page
{
    private String URL = "https://yopmail.com/en/wm";

    @FindBy(css = "#mail")
    WebElement mailBody;

    @FindBy(css = "iframe#ifmail")
    WebElement mailBodyFrame;

    public MailPage()
    {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT_IN_SECONDS), this);
    }

    public String getMailBody()
    {
        if(isPageStateCorrect())
        {
            return mailBody.getText();
        }

        return null;
    }

    @Override
    public boolean isPageAttributesCorrect()
    {
        return isIFrameCorrect() &&
               mailBody.isDisplayed();
    }

    @Override
    protected boolean changeToCorrectFrame()
    {
        try
        {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(mailBodyFrame);
        } catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.error("Tried to switch frames and it failed. ", e);
            return false;
        }

        return true;
    }

    static class InvalidMail extends MailPage
    {
        public InvalidMail()
        {
            log.error("Invalid Mail was created");
        }
        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    }
}
