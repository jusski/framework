package page;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends Page
{
    @FindBy(css = ".gsc-webResult.gsc-result")
    List<WebElement> resultRows;

    public SearchResultsPage()
    {
        PageFactory.initElements(driver, this);
    }

    public void findResultWithLinkTo(String link)
    {
        for(WebElement element : resultRows)
        {
            System.out.println(element.getText());
            System.out.println("****************************************");
        }
    }

    public SearchResultsPage open()
    {
        sleep().until(ExpectedConditions.visibilityOfAllElements(resultRows));
        return this;
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return driver.getTitle().startsWith("Search results");
    }

}
