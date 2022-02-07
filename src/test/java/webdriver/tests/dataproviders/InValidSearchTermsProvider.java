package webdriver.tests.dataproviders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.DataProvider;

public class InValidSearchTermsProvider
{
    @DataProvider
    public static Object[] inValidSearchTerms()
    {
        List<String> strings = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        return strings.toArray();
    }
}
