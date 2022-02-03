import java.io.File;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webdriver.page.google.cloud.calculator.CalculatorPage;
import webdriver.page.google.cloud.calculator.compute.engine.ComputeEnginePage;
import webdriver.page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import webdriver.page.mail.InboxPage;
import webdriver.page.mail.MailPage;
import webdriver.page.mail.MailProviderPage;
import webdriver.service.ComputeEngineModelCreator;


public class Main
{
    public static void main(String[] args) 
    {
        File file = new File("src/test/resources/model/dev/compute-engine-models/ComputeEngine1.model");
        ComputeEngineModel model = ComputeEngineModelCreator.withValuesFromYAML(file);
        
        ComputeEnginePage computeEngine = new CalculatorPage().open()
                .invokeComputeEngine()
                .fillFieldsFromModel(model);
                
        
        String estimatedCost = computeEngine.clickAddToEstimate()
                .returnEstimatedCost();
        System.out.println(estimatedCost);

        InboxPage inbox = new MailProviderPage().open().openInbox();
        
        computeEngine = computeEngine.clickEmailEstimate()
                .enterEmailAddress(inbox.getEmailAddress())
                .clickSendButton();

        MailPage mail = inbox.waitForEmailArrival(Duration.ofMinutes(2));
        String mailBody = mail.getMailBody();
        System.out.println(mailBody);
       
        
        System.out.println(mailBody.contains(estimatedCost));

    }

}
