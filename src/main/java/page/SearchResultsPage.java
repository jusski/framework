package page;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SearchResultsPage extends Page
{
    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    
    private final String searchString;
    
    @FindBy(css = ".gsc-webResult.gsc-result b")
    List<WebElement> resultRowsLink;
    
    By resultRowLocator = By.cssSelector(".gsc-webResult.gsc-result");

    public SearchResultsPage(String searchString)
    {
        this.searchString = searchString;
        PageFactory.initElements(driver, this);
    }

    public <T> T findResultWithLinkTo(String link, Class<T> page) throws InstantiationException, IllegalAccessException
    {
        for(WebElement element : resultRowsLink)
        {
            log.fine(element.getText());
            if(element.getText().startsWith(searchString))
            {
                log.info("Found Link" + element.getText());
                element.click();
                return page.newInstance();
            }
        }
        
        return null;
    }

    public SearchResultsPage open()
    {
        sleep().until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultRowLocator));
        return this;
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return driver.getTitle().startsWith("Search results");
    }

}
