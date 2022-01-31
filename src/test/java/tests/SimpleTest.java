package tests;

import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import page.util.IO;
import service.ComputeEngineModelCreator;
import tests.helpers.ScreenshotOnFailedBuildListener;

@Listeners({ScreenshotOnFailedBuildListener.class})
public class SimpleTest
{
    @DataProvider(name = "computeenginemodel")
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

    @Test(dataProvider = "computeenginemodel")
    public void test(ComputeEngineModel model)
    {
        System.out.println(model);
        System.out.println("????");
        Assert.assertFalse(true);
    }

}
