package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import tests.dataproviders.InValidSearchTermsProvider;
import tests.dataproviders.ValidSearchTermsProvider;
import tests.helpers.ScreenshotOnFailedBuildListener;

@Listeners({ScreenshotOnFailedBuildListener.class})
public class SmokeTest //extends AbstractTest
{
    @Test(dataProviderClass = ValidSearchTermsProvider.class,
          dataProvider = "validSearchTerms",
          description = "Should find link to valid search term on 1 page in search results of google.cloud page")
    public void shouldFindSearchTermOnFirstPageOfSearchResults(String searchTerm) 
    {
//        Page page = new MainPage().open()
//                .invokeSearch(searchTerm)
//                .findResultWithLinkTo(searchTerm, Page.class);
//
//        Assert.assertTrue(page.isPageStateCorrect(), "Could not find link to valid search term: " + searchTerm);
    }
    
    @Test(dataProviderClass = InValidSearchTermsProvider.class,
          dataProvider = "inValidSearchTerms",
          description = "Should fail to find link to random search term on 1 page in search results of google.cloud page")
      public void shouldFailToFindSearchTermOnFirstPageOfSearchResults(String searchTerm) 
      {
        System.out.println("ky");
//          Page page = new MainPage().open()
//                  .invokeSearch(searchTerm)
//                  .findResultWithLinkTo(searchTerm, Page.class);
//
//          Assert.assertFalse(page.isPageStateCorrect(), "Managed to find valid link to search term: " + searchTerm);
      }

}
