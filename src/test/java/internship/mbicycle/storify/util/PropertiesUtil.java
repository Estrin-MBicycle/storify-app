package internship.mbicycle.storify.util;

import lombok.SneakyThrows;

import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = createPropertiesMap();

    private PropertiesUtil() {
    }

    public static String getProperty(String name) {
        return PROPERTIES.getProperty(name);
    }

    @SneakyThrows
    private static Properties createPropertiesMap() {
        final Properties properties = new Properties();
        properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        return properties;
    }
}
