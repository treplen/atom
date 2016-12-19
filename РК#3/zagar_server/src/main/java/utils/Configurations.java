package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by svuatoslav on 11/28/16.
 */
public final class Configurations {
    private final static Logger log = LogManager.getLogger(Configurations.class);

    private static PropertiesReader reader;

    static {
        reader = new PropertiesReader("src/main/resources/config.properties");
    }

    public static int getIntProperty(String name)
    {
        return reader.getIntProperty(name);
    }

    public static String getStringProperty(String name)
    {
        return reader.getStringProperty(name);
    }

    public static List<String> getListProperty(String name)
    {
        return reader.getListProperty(name);
    }
}
