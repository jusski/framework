import java.time.LocalDateTime;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.xml.internal.Parser;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
class Order
{
    public Integer a;
    public String b;
}

@Log4j2
public class Track
{
    public static void main(String[] args)
    {
        WebDriverManager.chromiumdriver().driverVersion("97.0.4692.71").setup();
        ChromeDriver driver = new ChromeDriver(new ChromeOptions().setBinary("C:\\Users\\R61\\AppData\\Local\\Chromium\\Application\\chrome.exe"));
        System.out.println("end");
        driver.get("https://cloud.google.com/products/calculator");
        
    }

}
