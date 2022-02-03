package webdriver.page.google.cloud.calculator.compute.engine;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.extern.log4j.Log4j2;
import webdriver.page.Page;
import webdriver.page.google.cloud.calculator.compute.engine.model.ComputeEngineModel;
import webdriver.page.util.Exceptions;

@Log4j2
public class ComputeEnginePage extends Page
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

    @FindBy(css = "devsite-iframe > iframe")
    WebElement outerFrame;
    
    @FindBy(css = "#myFrame")
    WebElement mainFrame;
    
    public ComputeEnginePage()
    {
        PageFactory.initElements(driver, this);
    }
    
    public ComputeEnginePage fillFieldsFromModel(ComputeEngineModel model)
    {
        if(isPageStateCorrect())
        {
            ComputeEnginePage computeEngine = setNumberInstances(model.getNumberOfInstances())
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
    
    public ComputeEnginePage setNumberInstances(int count)
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
                log.error(String.format("When invoking with parameter [%d], exception was caught: %s", count, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }

        return this;
    }

    public ComputeEnginePage setOerationSystem(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }

        return this;
    }

    public ComputeEnginePage setMachineClass(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    

    public ComputeEnginePage setSeries(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEnginePage setMachineType(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEnginePage addGPU(String gpuName, int count)
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
                log.error(String.format("When invoking with parameter [%s, %d], exception was caught: %s", gpuName, count, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEnginePage addSsd(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEnginePage setDataCenterLocation(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

                return new InvalidComputeEngine();
            }
        }
        return this;
    }

    public ComputeEnginePage setCommitedUsage(String value)
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
                log.error(String.format("When invoking with parameter [%s], exception was caught: %s", value, Exceptions.printStackTraceToString(e)));

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

    public ComputeEnginePage clickAddToEstimate()
    {
        if(isPageStateCorrect())
        {
            try
            {   
                waitFor(() -> isEnabled(estimateCostForComputeEngineFormButton));
                scrollIntoViewAndClick(estimateCostForComputeEngineFormButton);
                waitFor(() -> isDisplayed(emailestimateButton));
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.error("Exception was caught. ", e);

                return new InvalidComputeEngine();
            }
        }
        return this;
    }
    
    public String returnEstimatedCost()
    {
        if(isPageStateCorrect())
        {
            try
            {
                scrollIntoView(totalEstimatedCostText);
                
                Matcher matcher = Pattern.compile("\\ +Cost:[\\D]+\\ +([\\d,.]+)")
                        .matcher(totalEstimatedCostText.getText());
                matcher.find();
                log.trace("Estimated cost in usd: {}", matcher.group(1));
                return matcher.group(1);
            }
            catch(NoSuchElementException | TimeoutException e)
            {
                log.error(String.format("Exception was caught: %s", Exceptions.printStackTraceToString(e)));
            }
        }
        
        return null;
    }

    public EmailEstimateFormPage clickEmailEstimate()
    {
        if(isPageStateCorrect())
        {
            try
            {
                scrollIntoViewAndClick(emailestimateButton);
            }
            catch(NoSuchElementException | ElementClickInterceptedException e)
            {
                log.error("Exception was caught. ", e);
                isValid = false;
            }
        }
        return new EmailEstimateFormPage(isValid, this);
    }

    @Override
    public boolean isPageAttributesCorrect()
    {
        return isIFrameCorrect() &&
               computeEngineBlock.isDisplayed();
    }
    
    @Override
    protected boolean changeToCorrectFrame()
    {
        try
        {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(outerFrame).switchTo().frame(mainFrame); 
            frameId = driver.getFrameId();
        }
        catch (NoSuchFrameException | StaleElementReferenceException | NoSuchElementException e)
        {
            log.warn("Tried to switch frames and it failed. ", e);
            return false;
        }

        return true;
    }

    private class InvalidComputeEngine extends ComputeEnginePage
    {
        boolean isValid = false;
        
        @Override
        public boolean isPageStateCorrect()
        {
            return false;
        }
    };
}
