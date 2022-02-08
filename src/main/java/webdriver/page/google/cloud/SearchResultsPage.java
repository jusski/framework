package webdriver.page.google.cloud;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;

@Log4j2
public class SearchResultsPage extends Page
{
    @FindBy(css = ".gsc-webResult.gsc-result a")
    List<WebElement> resultRowsLink;

    public SearchResultsPage(String windowHandle)
    {
        PageFactory.initElements(driver, this);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T extends Page> T findResultWithLinkTo(String link, Class<? extends Page> page)
    {
        T result = (T)page.newInstance();
        ((Page)result).isValid = false;
        
        try
        {
            waitFor(() -> !resultRowsLink.isEmpty()); 
            for(WebElement element : resultRowsLink)
            {
                log.trace(element.getText());
                if(element.getText().startsWith(link))
                {
                    log.trace("Found Link" + element.getText());
                    element.click();
                    result = (T)page.newInstance();
                    break;
                }
            }
        }
        catch(TimeoutException e)
        {
            log.warn(e);
        }

        return result;
    }

    @Override
    protected boolean isPageAttributesCorrect()
    {
        return driver.getTitle().startsWith("Search results");
    }

}
