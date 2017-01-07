package com.itinapi.conf;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hamehta3 on 12/31/16.
 */
public class ServletContextClass implements ServletContextListener
{
    private static final String DB_PROPERTIES_FILE = "db.properties";
    private static final Properties DB_PROPERTIES = new Properties();
    private static DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            loadPropertiesFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

    private static void loadPropertiesFile() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(DB_PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new IOException("Properties file '" + DB_PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            DB_PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            throw new IOException("Cannot load properties file '" + DB_PROPERTIES_FILE + "': " + e.getMessage());
        }
    }

    public static DataSource getDataSource() throws NamingException {
        if (dataSource == null) {
            //System.out.println("Looking up datasource");
            dataSource = (DataSource) new InitialContext().lookup(DB_PROPERTIES.getProperty("jndiUrl"));
            return dataSource;
        } else {
            //System.out.println("Returning existing datasource");
            return dataSource;
        }
    }
}