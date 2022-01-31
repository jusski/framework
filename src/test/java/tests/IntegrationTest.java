package tests;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import page.Page;
import page.google.cloud.MainPage;
import page.google.cloud.calculator.Calculator;
import page.google.cloud.calculator.compute.engine.ComputeEngine;
import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import page.mail.Inbox;
import page.mail.Mail;
import page.mail.MailProvider;
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

        Assert.assertTrue(page.isPageStateCorrect());
    }

    @Test(dependsOnMethods = "shouldFindSearchTermOnFirstPageOfSearchResults",
          description = "Should successfully open cloud platform pricing calulator link")
    public void shouldFindCloudCalculatorOnSearchResults()
    {
        Calculator calculator = new MainPage().open()
                .invokeSearch("Google Cloud Platform Pricing Calculator")
                .findResultWithLinkTo("Google Cloud Platform Pricing Calculator", Calculator.class);

        Assert.assertTrue(calculator.isPageStateCorrect());

    }

    @Test(description = "Should successfully apply model data to compute engine in cloud platform pricing calculator")
    public void shouldFillComputeEngineFormAndGetEstimate(ComputeEngineModel model)
    {
        ComputeEngine computeEngine = new Calculator().open()
                .invokeComputeEngine().fillFieldsFromModel(model);
        
        Assert.assertTrue(computeEngine.isPageStateCorrect(), 
                          String.format("Could not deserialize model to compute engine in google cloud calculator.\n" +
                                        "tried to apply model: %s", model.toString()));
    }
    
    @Test(description = "Tries to create temporary email inbox in 'https://yopmail.com/en/' provider")
    public void shouldCreateTemporaryInbox()
    {
        Inbox inbox = new MailProvider().open().openInbox();
        
        Assert.assertTrue(inbox.isPageStateCorrect(), "Could not create temporary email inbox.");
    }
    
    @Test(dependsOnMethods = {"shouldFillComputeEngineFormAndGetEstimate", "shouldCreateTemporaryInbox"},
          dataProviderClass = ComputeEngineModelDataProvider.class)
    public void shouldFillComputeEngineFormAndSendEmailEstimate(ComputeEngineModel model)
    {
        ComputeEngine computeEngine = new Calculator().open()
                .invokeComputeEngine().fillFieldsFromModel(model);
                
        
        String estimatedCost = computeEngine.returnEstimatedCost();
        
        Inbox inbox = new MailProvider().open().openInbox();
        
        computeEngine = computeEngine.clickEmailEstimate()
        .enterEmailAddress(inbox.getEmailAddress())
        .clickSendButton();
        
        Mail mail = inbox.waitForEmailArrival(Duration.ofMillis(2));
        String mailBody = mail.getMailBody();
        
        
        Assert.assertTrue(mailBody.contains(estimatedCost), "");
        
    }

}
