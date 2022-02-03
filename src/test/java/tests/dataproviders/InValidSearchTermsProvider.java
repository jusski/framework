package tests.dataproviders;

import java.util.UUID;

import org.testng.annotations.DataProvider;

import lombok.SneakyThrows;

public class InValidSearchTermsProvider
{
    @SneakyThrows
    @DataProvider
    public static Object[][] inValidSearchTerms()
    {
        return new Object[][] { {UUID.randomUUID().toString()} };
    }
}
