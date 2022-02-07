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
    public static Object[] validSearchTerms()
    {
        String path = "src/test/resources/model/dev/searching/ValidSearchTerm.data";
        List<String> validTerms = Files.readAllLines(Paths.get(path));
        return validTerms.toArray();
    }
}
