package webdriver.service;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;

import lombok.SneakyThrows;
import webdriver.page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;

public class ComputeEngineModelCreator
{
    @SneakyThrows
    public static ComputeEngineModel withValuesFromYAML(File file)
    {
        ObjectMapper mapper = new ObjectMapper(new JavaPropsFactory());
        return mapper.readValue(file, ComputeEngineModel.class);
    }
}
