import page.Calculator;
import page.ComputeEngine;
import page.MainPage;

public class Main
{

    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException
    {
//       new MainPage()
//       .open()
//       .invokeSearch("Google Cloud Platform Pricing Calculator")
//       .findResultWithLinkTo("Google Cloud Pricing Calculator", Calculator.class)
//       .invokeComputeEngine();
//      

       boolean correct = new Calculator().open()
               .invokeComputeEngine()
               .invokeOperationSystemSelectionWith("Free")
               .invokeMachineClassSelectionWith("Regular")
               .invokeSeriesSelectionWith("N1")
               .invokeMachinetypeSelectionWith("n1-standard-8")
               .invokeAddGPU("NVIDIA Tesla V100", 1)
               .invokeSsdSelectionWith("2x375")
               .invokeDataCenterLocationSelection("Frankfurt")
               .invokeCommitedUsageSelect("1 Year")
               .isPageStateCorrect();
       
       System.out.println(correct);
       System.out.println("end");
    }

}
