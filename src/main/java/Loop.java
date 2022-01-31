
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.base.Functions;

interface WebElement
{
    WebElement click();
    WebElement center();
}
public class Loop
{
//    WebElement computeEngineBlock = findBy(".compute-engine-block");
//    WebElement numberOfInstancesInput = findBy("input[ng-model='listingCtrl.computeServer.quantity']");
//    WebElement instancesDescriptionInput = findBy("input[ng-model='listingCtrl.computeServer.label']");
//    WebElement operatingSystemSelect = findBy("md-select[ng-model='listingCtrl.computeServer.os']");
//    WebElement machineClassSelect = findBy("md-select[ng-model='listingCtrl.computeServer.class']");
//    WebElement seriesSelect = findBy("md-select[ng-model='listingCtrl.computeServer.series']");
//    WebElement machineTypeSelect = findBy("md-select[ng-model='listingCtrl.computeServer.instance']");
//    WebElement gpuCheckbox = findBy("md-checkbox[ng-model='listingCtrl.computeServer.addGPUs'");
//    WebElement gpuTypeSelect = findBy("md-select[ng-model='listingCtrl.computeServer.gpuType']");
//    WebElement gpuCountSelect = findBy("md-select[ng-model='listingCtrl.computeServer.gpuCount']");
//    WebElement ssdSelect = findBy("md-select[ng-model='listingCtrl.computeServer.ssd']");
//    WebElement dataCenterLocationSelect = findBy("md-select[ng-model='listingCtrl.computeServer.location']");
    
//    public static WebElement findBy(String string)
//    {
////        return new We
////    }
    static 
    {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n"); 
    }
    
//    public a()
//    {
//        computeEngineBlock.center().click();
//        
//    }
    public static Logger log = Logger.getLogger(Loop.class.getName());
    public static void main(String[] args)
    {
     
      List<String> asList = Arrays.asList("1", "2", "1", "1", "3");
      Map<String, Long> map = asList.stream().collect(Collectors
                              .groupingBy(e-> e, Collectors.mapping(e -> e, Collectors.counting())));
      
      System.out.println(map);
      
    }
   
}
