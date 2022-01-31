package page.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions
{
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void rethrowUnchecked(Throwable exception) throws T
    {
        throw (T) exception;
    }
    
    public static String printStackTraceToString(Throwable exception)
    {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer, false));
        return writer.toString();
    }

}
