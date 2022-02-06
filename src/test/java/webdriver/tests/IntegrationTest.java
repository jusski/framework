package webdriver.tests;

import java.time.Duration;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import webdriver.page.google.cloud.MainPage;
import webdriver.page.google.cloud.calculator.CalculatorPage;
import webdriver.page.google.cloud.calculator.compute.engine.ComputeEnginePage;
import webdriver.page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import webdriver.page.mail.InboxPage;
import webdriver.page.mail.MailPage;
import webdriver.page.mail.MailProviderPage;
import webdriver.tests.dataproviders.ComputeEngineModelDataProvider;

public class IntegrationTest extends AbstractTest
{
    private InboxPage inbox;
    private ComputeEnginePage computeEngine;
    private String address;
    private String password;
   
    private ComputeEngineModel model;
    
    @Factory
    public static Object[] integrationTestFactory()
    {
        ArrayList<Object> result = new ArrayList<>();
        for(ComputeEngineModel model : ComputeEngineModelDataProvider.computeEngineModel())
        {
            result.add(new IntegrationTest(model));
        }
        return result.toArray();
    }
    
    public IntegrationTest(ComputeEngineModel model)
    {
        this.model = model;
    }
    
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
          description = "Should successfully apply model data to compute engine in cloud platform pricing calculator")
    public void shouldFillComputeEngineFormAndGetEstimate()
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
        inbox = new MailProviderPage()
                .open()
                .openInbox();

        Assert.assertTrue(inbox.isPageStateCorrect(), "Could not create temporary email inbox.");
    }

    @Test(dependsOnMethods = {"shouldCreateTemporaryInbox" , "shouldSendEmailEstimateToEmailAddress"})
    public void shouldReceiveEmailWithTotalEstimatedCost()
    {
        MailPage mail = inbox.waitForEmailArrival(Duration.ofMinutes(2));
        String mailBody = mail.getMailBody();
        
        Assert.assertTrue(mailBody.contains(computeEngine.returnEstimatedCost()), "Email body doesnt contain digit simila to compute engine estimate");
    }
    
    @Test(dependsOnMethods = {"shouldFillComputeEngineFormAndGetEstimate", "shouldCreateTemporaryInbox"})
    public void shouldSendEmailEstimateToEmailAddress(ComputeEngineModel model)
    {
        computeEngine.clickAddToEstimate()
                     .clickEmailEstimate()
                     .enterEmailAddress(inbox.getEmailAddress())
                     .clickSendButton();

        Assert.assertTrue(computeEngine.isPageStateCorrect(), "Failed to send estimate to email address");
    }

}
