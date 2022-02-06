import java.time.LocalDateTime;

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
        System.out.println(WebDriverManager.chromiumdriver().driverVersion("92.0.4515.107").getBrowserPath());
    }

}
