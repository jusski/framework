package tests.dataproviders;

import java.io.File;
import java.util.List;

import org.testng.annotations.DataProvider;

import page.util.IO;
import service.ComputeEngineModelCreator;


//@DataProvider(name = "test1")
//public Iterator<MyCustomData> createData() {
//  return Arrays.asList(new MyCustomData()).iterator();
//}

public class ComputeEngineModelDataProvider
{
    @DataProvider
    public static Object[][] computeEngineModel()
    {
        String environment = System.getProperty("environment", "dev");
        String path = "src/test/resources/model/" + environment + "/compute-engine-models/";
        
        List<File> files = IO.listFiles(path, "*.model");
        
        Object[][] computeEngineModel = new Object[files.size()][1];
        for(int i = 0; i < files.size(); ++i)
        {
            computeEngineModel[i][0] = ComputeEngineModelCreator.withValuesFromYAML(files.get(i));
        }
        
        return computeEngineModel;
    }
}
