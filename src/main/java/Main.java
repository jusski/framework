import java.io.File;
import java.time.Duration;

import org.testng.Assert;

import page.google.cloud.calculator.Calculator;
import page.google.cloud.calculator.compute.engine.ComputeEngine;
import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import page.mail.Inbox;
import page.mail.Mail;
import page.mail.MailProvider;
import service.ComputeEngineModelCreator;

public class Main
{
    public static void main(String[] args) 
    {
        File file = new File("src/test/resources/model/dev/compute-engine-models/ComputeEngine1.model");
        ComputeEngineModel model = ComputeEngineModelCreator.withValuesFromYAML(file);
        
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
