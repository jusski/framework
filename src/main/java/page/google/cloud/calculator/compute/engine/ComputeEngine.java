package page.google.cloud.calculator.compute.engine;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import page.Page;

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

    @FindBy(css = "div.md-select-menu-container.md-active")
    WebElement selectMenuContainer;

    @FindBy(css = "div.md-select-menu-container.md-active md-option")
    List<WebElement> selectMenuContainerOptions;

    private int frameId;
    
    public ComputeEngine()
    {
        this.frameId = driver.getFrameId();
        PageFactory.initElements(driver, this);
    }
    
    public ComputeEngine invokeSetNumberInstances(int count)
    {
        try
        {
            scrollIntoViewAndClick(numberOfInstancesInput);
            numberOfInstancesInput.sendKeys(String.valueOf(count));
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeOperationSystemSelectionWith(String value)
    {
        try
        {
            scrollIntoViewAndClick(operatingSystemSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeMachineClassSelectionWith(String value)
    {
        try
        {
            scrollIntoViewAndClick(machineClassSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    

    public ComputeEngine invokeSeriesSelectionWith(String value)
    {
        try
        {
            scrollIntoViewAndClick(seriesSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeMachinetypeSelectionWith(String value)
    {
        try
        {
            scrollIntoViewAndClick(machineTypeSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeAddGPU(String gpuName, int count)
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
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeSsdSelectionWith(String value)
    {
        try
        {
            scrollIntoViewAndClick(ssdSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeDataCenterLocationSelection(String value)
    {
        try
        {
            scrollIntoViewAndClick(dataCenterLocationSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public ComputeEngine invokeCommitedUsageSelect(String value)
    {
        try
        {
            scrollIntoViewAndClick(commitedUsageSelect);
            clickSelectionMenuOption(value);
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
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
            throw new NoSuchElementException(String.format("Could not found %s element in selection menu options", value));
        }
    }

    public ComputeEngine clickAddToEstimate()
    {
        try
        {   
            waitFor(() -> isEnabled(estimateCostForComputeEngineFormButton));
            scrollIntoViewAndClick(estimateCostForComputeEngineFormButton);
            waitFor(() -> isDisplayed(emailestimateButton));
        }
        catch(NoSuchElementException | TimeoutException e)
        {
            return new InvalidComputeEngine();
        }

        return this;
    }

    public EmailEstimateForm clickEmailEstimate()
    {
        scrollIntoViewAndClick(emailestimateButton);
        return new EmailEstimateForm(isValid, this);
    }

    @Override
    public boolean isPageStateCorrect()
    {
        return  isValid &&
                frameId == driver.getFrameId() &&
                driver.getCurrentUrl().startsWith(URL) && 
                computeEngineBlock.isDisplayed();
    }

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
