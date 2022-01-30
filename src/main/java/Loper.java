import java.util.logging.Logger;

class As
{
    static Logger log = Logger.getLogger(As.class.getName());
}

class Bs extends As
{
    public void b()
    {
        log.warning("some warning");
    }
}
public class Loper
{
    public static void main(String[] args)
    {
        new Bs().b();
    }
}
