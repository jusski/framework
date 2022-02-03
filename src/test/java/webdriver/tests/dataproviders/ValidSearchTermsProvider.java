package webdriver.tests.dataproviders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.DataProvider;

import lombok.SneakyThrows;

public class ValidSearchTermsProvider
{
    @SneakyThrows
    @DataProvider
    public static Object[][] validSearchTerms()
    {
        List<String> validTerms = Files.readAllLines(Paths.get("src/test/resources/model/dev/searching/ValidSearchTerm.data"));
        Object[][] validSearchTerms = new Object[validTerms.size()][1];
        
        for(int i = 0; i < validTerms.size(); ++i)
        {
            validSearchTerms[i][0] = validTerms.get(i);
        }
        return validSearchTerms;
    }
}
