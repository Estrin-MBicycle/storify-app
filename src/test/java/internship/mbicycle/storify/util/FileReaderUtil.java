package internship.mbicycle.storify.util;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

public class FileReaderUtil {

    public static String readFromJson(String path) throws Exception {
        File resource = new ClassPathResource(path).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }
}
