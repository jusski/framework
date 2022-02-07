package webdriver.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import webdriver.page.Page;
import webdriver.page.google.cloud.MainPage;
import webdriver.tests.dataproviders.InValidSearchTermsProvider;
import webdriver.tests.dataproviders.ValidSearchTermsProvider;

@Test(groups = {"cloud-calculator"})
public class GoogleSearch extends AbstractTest
{
    @Test(dataProviderClass = ValidSearchTermsProvider.class, dataProvider = "validSearchTerms", description = "Should find link to valid search term on 1 page in search results of google.cloud page")
    public void shouldFindSearchTermOnFirstPageOfSearchResults(String searchTerm)
    {
        Page page = new MainPage().open()
                .invokeSearch(searchTerm)
                .findResultWithLinkTo(searchTerm, Page.class);

        Assert.assertTrue(page.isPageStateCorrect(), "Could not find link to valid search term: " + searchTerm);
    }

    @Test(dataProviderClass = InValidSearchTermsProvider.class, dataProvider = "inValidSearchTerms", description = "Should fail to find link to random search term on 1 page in search results of google.cloud page")
    public void shouldFailToFindSearchTermOnFirstPageOfSearchResults(String searchTerm)
    {
        Page page = new MainPage().open()
                .invokeSearch(searchTerm)
                .findResultWithLinkTo(searchTerm, Page.class);

        Assert.assertFalse(page.isPageStateCorrect(), "Managed to find valid link to search term: " + searchTerm);
    }

}
