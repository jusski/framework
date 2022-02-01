package page.google.cloud;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import page.Page;

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
        waitFor(() -> !resultRowsLink.isEmpty()); // Note we should know how many results search page should return
        for(WebElement element : resultRowsLink)
        {
            log.trace(element.getText());
            if(element.getText().startsWith(link))
            {
                log.trace("Found Link" + element.getText());
                element.click();
                return (T)page.newInstance();
            }
        }
        T  result = (T)page.newInstance();
        ((Page) result).isValid = false;
        return result;
    }

    
    @Override
    protected boolean isPageAttributesCorrect()
    {
        return driver.getTitle().startsWith("Search results");
    }

}
