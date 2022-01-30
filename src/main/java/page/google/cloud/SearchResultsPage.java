package page.google.cloud;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import page.Page;


public class SearchResultsPage extends Page
{
    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    
    @FindBy(css = ".gsc-webResult.gsc-result a")
    List<WebElement> resultRowsLink;
    
    public SearchResultsPage()
    {
        PageFactory.initElements(driver, this);
    }

    public <T> T findResultWithLinkTo(String link, Class<T> page) throws InstantiationException, IllegalAccessException
    {
        waitFor(() -> !resultRowsLink.isEmpty());
        for(WebElement element : resultRowsLink)
        {
            log.fine(element.getText());
            if(element.getText().startsWith(link))
            {
                log.fine("Found Link" + element.getText());
                element.click();
                return page.newInstance();
            }
        }
        T  result = page.newInstance();
        ((Page) result).isValid = false;
        return result;
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return driver.getTitle().startsWith("Search results");
    }

}
