import page.MainPage;
import page.Page;

public class Main
{

    public static void main(String[] args)
    {
       new MainPage()
       .open()
       .invokeSearch("Google Cloud Platform Pricing Calculator")
       .findResultWithLinkTo("Google Cloud Pricing Calculator");

       System.out.println("end");
    }

}
