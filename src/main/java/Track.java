import java.io.IOException;

import lombok.extern.log4j.Log4j2;

class A
{
    static String url = "liaw";
    public String get() { return this.url;}
}

class B extends A
{
    static String url = "kriu";
}

@Log4j2
public class Track
{

    public static void main(String[] args) throws IOException
    {
        System.out.println(new B().get());
    }

}
