package com.itinapi.dao;

import com.itinapi.conf.ServletContextClass;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by hamehta3 on 10/29/16.
 */
public abstract class DAOFactory {

    private static DAOFactory instance;

    public static DAOFactory getInstance() throws NamingException {
        // For now just data source instance
        DataSource dataSource;
        dataSource = ServletContextClass.getDataSource();  // FIXME: bad practice?
        instance = new DataSourceDAOFactory(dataSource);
        return instance;
    }

    public abstract Connection getConnection() throws SQLException;
}
