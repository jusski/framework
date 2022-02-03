import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
class A
{
    public Integer a;
    public String b;
}

@Log4j2
public class Track
{
    public static void main(String[] args)
    {
       log.trace("trace");
       log.warn("warn");
       log.error("error");
       log.info("@|KeyStyle {}|@ = @|ValueStyle {}|@", 1,2);
       
    
    }

}
