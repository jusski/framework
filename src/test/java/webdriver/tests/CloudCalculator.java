package webdriver.tests;

import java.time.Duration;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@Test(groups = "cloud-calculator")
public class CloudCalculator extends AbstractTest
{
    InboxPage inbox;
    ComputeEnginePage computeEngine;
    ComputeEngineModel model;
    CalculatorPage calculator;
    
    @Factory
    public static Object[] integrationTestFactory()
    {
        ArrayList<Object> result = new ArrayList<>();
        for(ComputeEngineModel model : ComputeEngineModelDataProvider.computeEngineModel())
        {
            result.add(new CloudCalculator(model));
        }
        return result.toArray();
    }
    
    @BeforeClass
    public void createTemporaryInbox()
    {
        inbox = new MailProviderPage()
                .open()
                .openInbox();
    }
    
    public CloudCalculator(ComputeEngineModel model)
    {
        this.model = model;
    }
    
    @Test(description = "Should successfully open cloud platform pricing calulator link")
    public void shouldFindCloudCalculatorOnSearchResults()
    {
        calculator = new MainPage()
                     .open()
                     .invokeSearch("Google Cloud Platform Pricing Calculator")
                     .findResultWithLinkTo("Google Cloud Platform Pricing Calculator", CalculatorPage.class);                                                              

        Assert.assertTrue(calculator.isPageStateCorrect());
    }

    @Test(dependsOnMethods = "shouldFindCloudCalculatorOnSearchResults",
          description = "Should successfully apply model data to compute engine in cloud platform pricing calculator")
    public void shouldFillComputeEngineFormAndGetEstimate()
    {
        computeEngine = calculator.invokeComputeEngine()
                                  .fillFieldsFromModel(model);
        
        Assert.assertTrue(computeEngine.isPageStateCorrect(), String.format("Could not deserialize model %s to compute "
                + "engine in google cloud calculator", model));
    }
    

    @Test(dependsOnMethods = {"shouldSendEmailEstimateToEmailAddress"})
    public void shouldReceiveEmailWithTotalEstimatedCost()
    {
        MailPage mail = inbox.waitForEmailArrival(Duration.ofMinutes(2));
        String mailBody = mail.getMailBody();
        
        Assert.assertTrue(mailBody.contains(computeEngine.returnEstimatedCost()), "Email body doesnt contain digit simila to compute engine estimate");
    }
    
    @Test(dependsOnMethods = {"shouldFillComputeEngineFormAndGetEstimate"})
    public void shouldSendEmailEstimateToEmailAddress()
    {
        computeEngine.clickAddToEstimate()
                     .clickEmailEstimate()
                     .enterEmailAddress(inbox.getEmailAddress())
                     .clickSendButton();

        Assert.assertTrue(computeEngine.isPageStateCorrect(), "Failed to send estimate to email address");
    }

}
