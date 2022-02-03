package service;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.SneakyThrows;
import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;

public class ComputeEngineModelCreator
{
    @SneakyThrows
    public static ComputeEngineModel withValuesFromYAML(File file)
    {
        //TODO change to properties
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(file, ComputeEngineModel.class);
    }
}
