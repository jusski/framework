package webdriver.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import webdriver.page.mail.InboxPage;
import webdriver.page.mail.MailProviderPage;

@Test(groups = {"mail", "smoke"})
public class Mail extends AbstractTest
{
    @Test(description = "Tries to create temporary email inbox in 'https://yopmail.com/en/' provider")
    public void shouldCreateTemporaryInbox()
    {
        InboxPage inbox = new MailProviderPage()
                          .open()
                          .openInbox();

        Assert.assertTrue(inbox.isPageStateCorrect(), "Could not create temporary email inbox.");
    }

}
