package webdriver.tests.dataproviders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import webdriver.page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import webdriver.page.util.IO;
import webdriver.service.ComputeEngineModelCreator;


public class ComputeEngineModelDataProvider
{
    @DataProvider
    public static ComputeEngineModel[] computeEngineModel()
    {
        String environment = System.getProperty("environment", "dev");
        String path = "src/test/resources/model/" + environment + "/compute-engine-models/";
        
        List<File> files = IO.listFiles(path, "*.model_compute_engine");
        
        ArrayList<ComputeEngineModel> computeEngineModel = new ArrayList<>();
        for(int i = 0; i < files.size(); ++i)
        {
            computeEngineModel.add(ComputeEngineModelCreator.withValuesFromProperties(files.get(i)));
        }
        
        return computeEngineModel.toArray(new ComputeEngineModel[0]);
    }
}
