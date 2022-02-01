package tests;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import page.Page;
import page.google.cloud.MainPage;
import page.google.cloud.calculator.CalculatorPage;
import page.google.cloud.calculator.compute.engine.ComputeEnginePage;
import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import page.mail.InboxPage;
import page.mail.MailPage;
import page.mail.MailProviderPage;
import tests.dataproviders.ComputeEngineModelDataProvider;

public class IntegrationTest extends AbstractTest
{
    @Test(description = "Should find link to 'Google Cloud Platform Pricing Calculator' after entering " + 
                        "'Google Cloud Platform Pricing Calculator' in search field of google.cloud page")
    public void shouldFindSearchTermOnFirstPageOfSearchResults() throws InstantiationException, IllegalAccessException, InterruptedException
    {
        Page page = new MainPage().open()
                .invokeSearch("Google Cloud Platform Pricing Calculator")
                .findResultWithLinkTo("Google Cloud Platform Pricing Calculator", Page.class);

//        Assert.assertTrue(page.isPageStateCorrect());
    }

    @Test(dependsOnMethods = "shouldFindSearchTermOnFirstPageOfSearchResults",
          description = "Should successfully open cloud platform pricing calulator link")
    public void shouldFindCloudCalculatorOnSearchResults()
    {
        CalculatorPage calculator = new MainPage().open()
                .invokeSearch("Google Cloud Platform Pricing Calculator")
                .findResultWithLinkTo("Google Cloud Platform Pricing Calculator", CalculatorPage.class);

        Assert.assertTrue(calculator.isPageStateCorrect());

    }

    @Test(description = "Should successfully apply model data to compute engine in cloud platform pricing calculator")
    public void shouldFillComputeEngineFormAndGetEstimate(ComputeEngineModel model)
    {
        ComputeEnginePage computeEngine = new CalculatorPage().open()
                .invokeComputeEngine().fillFieldsFromModel(model);
        
        Assert.assertTrue(computeEngine.isPageStateCorrect(), 
                          String.format("Could not deserialize model to compute engine in google cloud calculator.\n" +
                                        "tried to apply model: %s", model.toString()));
    }
    
    @Test(description = "Tries to create temporary email inbox in 'https://yopmail.com/en/' provider")
    public void shouldCreateTemporaryInbox()
    {
        InboxPage inbox = new MailProviderPage().open().openInbox();
        
        Assert.assertTrue(inbox.isPageStateCorrect(), "Could not create temporary email inbox.");
    }
    
    @Test(dependsOnMethods = {"shouldFillComputeEngineFormAndGetEstimate", "shouldCreateTemporaryInbox"},
          dataProviderClass = ComputeEngineModelDataProvider.class)
    public void shouldFillComputeEngineFormAndSendEmailEstimate(ComputeEngineModel model)
    {
        SoftAssert softAssert = new SoftAssert();

        ComputeEnginePage computeEngine = new CalculatorPage().open()
                .invokeComputeEngine().fillFieldsFromModel(model);
        softAssert.assertTrue(computeEngine.isPageStateCorrect(), 
                              String.format("Failed to deserialize model: %s to computeEngine.", model));
       
        int estimatedCost = computeEngine.clickAddToEstimate()
                .returnEstimatedCost();
        softAssert.assertTrue(computeEngine.isPageStateCorrect(), "Failed");
        InboxPage inbox = new MailProviderPage().open().openInbox();
        
        computeEngine = computeEngine.clickEmailEstimate()
        .enterEmailAddress(inbox.getEmailAddress())
        .clickSendButton();
        
        MailPage mail = inbox.waitForEmailArrival(Duration.ofMillis(2));
        String mailBody = mail.getMailBody();
        
        softAssert.assertTrue(mailBody.contains(String.valueOf(estimatedCost)), 
                              "Email body doesnt contain digit simila to compute engine estimate");
        softAssert.assertAll();
    }

}
