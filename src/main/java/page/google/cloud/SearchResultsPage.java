package page.google.cloud;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import lombok.SneakyThrows;
import page.Page;


public class SearchResultsPage extends Page
{
    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    
    @FindBy(css = ".gsc-webResult.gsc-result a")
    List<WebElement> resultRowsLink;

    private String windowHandle;
    
    public SearchResultsPage(String windowHandle)
    {
        this.windowHandle = driver.getWindowHandle();
        PageFactory.initElements(driver, this);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T extends Page> T findResultWithLinkTo(String link, Class<? extends Page> page) 
    {
        waitFor(() -> !resultRowsLink.isEmpty()); // Note we should know how many results search page should return
        for(WebElement element : resultRowsLink)
        {
            log.fine(element.getText());
            if(element.getText().startsWith(link))
            {
                log.fine("Found Link" + element.getText());
                element.click();
                return (T)page.newInstance();
            }
        }
        Constructor<? extends Page> constructor = page.getConstructor(String.class);
        T  result = (T)constructor.newInstance(windowHandle);
        ((Page) result).isValid = false;
        return result;
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return isValid &&
               ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
               driver.getTitle().startsWith("Search results");
    }

}
