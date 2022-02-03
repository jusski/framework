package tests;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

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
    private InboxPage inbox;
    private ComputeEnginePage computeEngine;
    private String estimatedCost;
    
    @Test(description = "Should successfully open cloud platform pricing calulator link")
    public void shouldFindCloudCalculatorOnSearchResults()
    {
        CalculatorPage calculator = new MainPage()
                .open()
                .invokeSearch("Google Cloud Platform Pricing Calculator")
                .findResultWithLinkTo("Google Cloud Platform Pricing Calculator", CalculatorPage.class);

        Assert.assertTrue(calculator.isPageStateCorrect());
    }

    @Test(dependsOnMethods = "shouldFindCloudCalculatorOnSearchResults",
          dataProviderClass = ComputeEngineModelDataProvider.class,
          dataProvider = "computeEngineModel",
          description = "Should successfully apply model data to compute engine in cloud platform pricing calculator")
    public void shouldFillComputeEngineFormAndGetEstimate(ComputeEngineModel model)
    {
        computeEngine = new CalculatorPage()
                .open()
                .invokeComputeEngine()
                .fillFieldsFromModel(model);
        
        Assert.assertTrue(computeEngine.isPageStateCorrect(), String.format("Could not deserialize model %s to compute "
                + "engine in google cloud calculator", model));
    }
    
   
    @Test(description = "Tries to create temporary email inbox in 'https://yopmail.com/en/' provider")
    public void shouldCreateTemporaryInbox()
    {
        inbox = new MailProviderPage().open().openInbox();
        
        Assert.assertTrue(inbox.isPageStateCorrect(), "Could not create temporary email inbox.");
    }
    
    @Test(dependsOnMethods = {"shouldCreateTemporaryInbox" , "shouldSendEmailEstimateToEmailAddress"})
    public void shouldReceiveEmailWithTotalEstimatedCost()
    {
        MailPage mail = inbox.waitForEmailArrival(Duration.ofMinutes(2));
        String mailBody = mail.getMailBody();
        
        Assert.assertTrue(mailBody.contains(computeEngine.returnEstimatedCost()), "Email body doesnt contain digit simila to compute engine estimate");
    }
    
    @Test(dependsOnMethods = {"shouldFillComputeEngineFormAndGetEstimate", "shouldCreateTemporaryInbox"},
          dataProviderClass = ComputeEngineModelDataProvider.class,
          dataProvider = "computeEngineModel")
    public void shouldSendEmailEstimateToEmailAddress(ComputeEngineModel model)
    {
        computeEngine.clickAddToEstimate()
                     .clickEmailEstimate()
                     .enterEmailAddress(inbox.getEmailAddress())
                     .clickSendButton();

        Assert.assertTrue(computeEngine.isPageStateCorrect(), "Failed to send estimate to email address");
    }

}
