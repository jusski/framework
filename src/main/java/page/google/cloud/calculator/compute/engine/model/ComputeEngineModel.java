package page.google.cloud.calculator.compute.engine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComputeEngineModel
{
    String commitedUsage;
    String dataCenterLocation;
    String gpuType;
    String instancesDescription;
    String machineClass;
    String machineType;
    String operatingSystem;
    String series;
    String ssd;

    int numberOfInstances;
    int gpuCount;
}
