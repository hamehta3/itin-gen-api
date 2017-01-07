package com.itinapi.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hamehta3 on 10/23/16.
 */
public class DataSourceDAOFactory extends DAOFactory {

    private DataSource dataSource;

    public DataSourceDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
