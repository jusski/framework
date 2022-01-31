package page.google.cloud.calculator.compute.engine;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.NoArgsConstructor;
import page.Page;
import page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import page.util.Exceptions;

@NoArgsConstructor
public class ComputeEngine extends Page
{
    private final String URL = "https://cloud.google.com/products/calculator";

    @FindBy(css = ".compute-engine-block")
    WebElement computeEngineBlock;

    @FindBy(css = "input[ng-model='listingCtrl.computeServer.quantity']")
    WebElement numberOfInstancesInput;

    @FindBy(css = "input[ng-model='listingCtrl.computeServer.label']")
    WebElement instancesDescriptionInput;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.os']")
    WebElement operatingSystemSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.class']")
    WebElement machineClassSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.series']")
    WebElement seriesSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.instance']")
    WebElement machineTypeSelect;

    @FindBy(css = "md-checkbox[ng-model='listingCtrl.computeServer.addGPUs'")
    WebElement gpuCheckbox;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.gpuType']")
    WebElement gpuTypeSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.gpuCount']")
    WebElement gpuCountSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.ssd']")
    WebElement ssdSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.location']")
    WebElement dataCenterLocationSelect;

    @FindBy(css = "md-select[ng-model='listingCtrl.computeServer.cud']")
    WebElement commitedUsageSelect;

    @FindBy(css = "form[name='ComputeEngineForm'] > div > button")
    WebElement estimateCostForComputeEngineFormButton;

    @FindBy(css = "#email_quote")
    WebElement emailestimateButton;
    
    @FindBy(css = "#compute b.ng-binding")
    WebElement totalEstimatedCostText;

    @FindBy(css = "div.md-select-menu-container.md-active")
    WebElement selectMenuContainer;

    @FindBy(css = "div.md-select-menu-container.md-active md-option")
    List<WebElement> selectMenuContainerOptions;

    private int frameId;

    private String windowHandle;
    
    public ComputeEngine(String windowHandle)
    { 
        this.windowHandle = driver.getWindowHandle();
        this.frameId = driver.getFrameId();
        PageFactory.initElements(driver, this);
    }
    
    public ComputeEngine fillFieldsFromModel(ComputeEngineModel model)
    {
        if(isPageStateCorrect())
        {
            ComputeEngine computeEngine = setNumberInstances(model.getNumberOfInstances())
                    .setOerationSystem(model.getOperatingSystem())
                    .setMachineClass(model.getMachineClass())
                    .setSeries(model.getSeries())
                    .setMachineType(model.getMachineType())
                    .addGPU(model.getGpuType(), model.getGpuCount())
                    .addSsd(model.getSsd())
                    .setDataCenterLocation(model.getDataCenterLocation())
                    .setCommitedUsage(model.getCommitedUsage());
            
            return computeEngine;
        }
        
        return this;
    }
    
    public ComputeEngine setNumberInstances(int count)
    {
        if(isValid)
        {
            try
            {
                scrollIntoViewAndClick(numberOfInstancesInput);
                numberOfInstancesInput.sendKeys(String.valueOf(count));
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%d], exception was caught: %s", count, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }

        return this;
    }

    public ComputeEngine setOerationSystem(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(operatingSystemSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }

        return this;
    }

    public ComputeEngine setMachineClass(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(machineClassSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    

    public ComputeEngine setSeries(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(seriesSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEngine setMachineType(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(machineTypeSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEngine addGPU(String gpuName, int count)
    {
        if(isValid && Objects.nonNull(gpuName))
        {
            try
            {
                scrollIntoViewAndClick(gpuCheckbox);
                waitFor(() -> isDisplayed(gpuTypeSelect));
                scrollIntoViewAndClick(gpuTypeSelect);
                clickSelectionMenuOption(gpuName);

                waitFor(() -> isDisplayed(gpuCountSelect));
                scrollIntoViewAndClick(gpuCountSelect);
                clickSelectionMenuOption(String.valueOf(count));
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s, %d], exception was caught: %s", gpuName, count, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEngine addSsd(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(ssdSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEngine setDataCenterLocation(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(dataCenterLocationSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEngine setCommitedUsage(String value)
    {
        if(isValid && Objects.nonNull(value))
        {
            try
            {
                scrollIntoViewAndClick(commitedUsageSelect);
                clickSelectionMenuOption(value);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    private void clickSelectionMenuOption(String value) throws NoSuchElementException
    {
        
        try
        {
            waitFor(() -> selectMenuContainerOptions.stream().anyMatch(e -> e.getText().contains(value)));
            for(WebElement element : selectMenuContainerOptions)
            {
                System.out.println(element.getText());
                if(element.getText().contains(value))
                {
                    scrollIntoViewAndClick(element);
                    waitFor(() -> selectMenuContainerOptions.isEmpty());
                    return;
                }
            }
        }
        catch(TimeoutException e)
        {
            throw new NoSuchElementException(String.format("Could not find %s element in selection menu options", value));
        }
    }

    public ComputeEngine clickAddToEstimate()
    {
        if(isValid)
        {
            try
            {   
                waitFor(() -> isEnabled(estimateCostForComputeEngineFormButton));
                scrollIntoViewAndClick(estimateCostForComputeEngineFormButton);
                waitFor(() -> isDisplayed(emailestimateButton));
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("Exception was caught: %s", Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }
    
    public String returnEstimatedCost()
    {
        if(isValid)
        {
            try
            {
                return totalEstimatedCostText.getText();
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.severe(String.format("Exception was caught: %s", Exceptions.printStackTraceToString(e)));

            }
        }
        
        return "";
    }

    public EmailEstimateForm clickEmailEstimate()
    {
        if(isValid)
        {
            try
            {
                scrollIntoViewAndClick(emailestimateButton);
            }
            catch(NoSuchElementException | ElementClickInterceptedException e)
            {
                log.severe(String.format("Exception was caught: %s", Exceptions.printStackTraceToString(e)));
                isValid = false;
            }
        }
        return new EmailEstimateForm(isValid, this, windowHandle);
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return  isValid &&
                ((driver.getWindowHandle().equals(windowHandle)) || (changeToCorrectWindow(windowHandle))) &&
                frameId == driver.getFrameId() &&
                driver.getCurrentUrl().startsWith(URL) && 
                computeEngineBlock.isDisplayed();
    }

    @NoArgsConstructor
    private class InvalidComputeEngine extends ComputeEngine
    {
        boolean isValid = false;
        
        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    };
}
