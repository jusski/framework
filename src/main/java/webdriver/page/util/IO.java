package webdriver.page.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.Files;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class IO
{

    public static List<File> listFiles(String path, String glob)
    {
        return listFiles(new File(path), glob);
    }

    @SneakyThrows
    public static List<File> listFiles(File path, String glob)
    {
        List<File> filesList;
        if(path.exists())
        {
        PathMatcher matcher = FileSystems.getDefault()
                .getPathMatcher("glob:**/" + glob);

        filesList = java.nio.file.Files.walk(path.toPath())
                .filter(java.nio.file.Files::isRegularFile)
                .filter(matcher::matches)
                .map(Path::toFile)
                .collect(Collectors.toList());
        }
        else
        {
            throw new IllegalArgumentException(String.format("Path: %s does not exists", path));
        }

        return filesList;
    }
    
    /**
     * If file exists tries to create file(n) name file
     *
     */
    public static File createFile(File file) 
    {
        try
        {
            Files.createParentDirs(file);
            if(!file.createNewFile())
            {
               
                String fileParent = file.getParent();
                String filename = file.getName();
                String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
                String name = filename.substring(0, filename.lastIndexOf('.'));

                String uniquePostfix = "(0)";
                int counter = 0;
                do
                {
                    file = new File(String.format("%s%c%s%s%s", fileParent, File.separatorChar, name, uniquePostfix, extension));
                    uniquePostfix = "(" + ++counter + ")";
                } while(!file.createNewFile());
            }
        }
        catch(IOException e)
        {
            log.error("Could not create file with path {}", file);
            Exceptions.rethrowUnchecked(e);
        }
       
        return file;
    }

}
