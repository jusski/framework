import java.time.Duration;

import org.openqa.selenium.WindowType;

import driver.CustomWebDriver;
import driver.Driver;
import page.google.cloud.calculator.Calculator;
import page.google.cloud.calculator.compute.engine.EmailEstimateForm;
import page.mail.Inbox;
import page.mail.MailProvider;


public class Main
{

    public static void checkMail()
    {
        MailProvider mail = new MailProvider().open();
        System.out.println(mail.getEmailAddress());
        Inbox inbox = mail.openInbox();
        System.out.println(inbox.waitForEmailArrival(Duration.ofMinutes(2)).getMailBody());
    }

    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException
    {

        // new MainPage()
        // .open()
        // .invokeSearch("Google Cloud Platform Pricing Calculator")
        // .findResultWithLinkTo("Google Cloud Pricing Calculator",
        // Calculator.class)
        // .invokeComputeEngine();
        //
        //
        EmailEstimateForm emailEstimate = new Calculator().open()
                .invokeComputeEngine()
                .invokeSetNumberInstances(4)
                .invokeOperationSystemSelectionWith("Free")
                .invokeMachineClassSelectionWith("Regular")
                .invokeSeriesSelectionWith("N1")
                .invokeMachinetypeSelectionWith("n1-standard-8")
                .invokeAddGPU("NVIDIA Tesla V100", 1)
                .invokeSsdSelectionWith("2x375")
                .invokeDataCenterLocationSelection("Frankfurt")
                .invokeCommitedUsageSelect("1 Year")
                .clickAddToEstimate()
                .clickEmailEstimate();

        CustomWebDriver driver = Driver.getInstance();
        String calculateHandle = driver.getWindowHandle();

        
        
        driver.switchTo().newWindow(WindowType.TAB);
        String inboxHandle = driver.getWindowHandle();
        Inbox inbox = new MailProvider().open().openInbox();
        String emailAddress = inbox.getEmailAddress();
        
        driver.switchTo().window(calculateHandle);
        emailEstimate.enterEmailAddress(emailAddress)
                     .clickSendButton();
        
        driver.switchTo().window(inboxHandle);
        String mailBody = inbox.waitForEmailArrival(Duration.ofMinutes(1)).getMailBody();
        System.out.println(mailBody);
        
        
        //         .enterEmailAddress("abrakadabra@google.com")
        //         .isPageStateCorrect();
        //        
        System.out.println("end");
    }

}
