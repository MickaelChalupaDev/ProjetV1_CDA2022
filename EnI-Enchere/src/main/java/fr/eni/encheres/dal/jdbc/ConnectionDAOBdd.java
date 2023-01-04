package fr.eni.encheres.dal.jdbc;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDAOBdd {
    private static DataSource dataSource;

    static {
        Context context;
        try {

            context = new InitialContext();
            ConnectionDAOBdd.dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_connexion");

        } catch (final Exception e) {
            throw new RuntimeException("Impossible d'accéder à la base de Données !!!");
        }

    }

    public static Connection getConnection() throws SQLException {
        return ConnectionDAOBdd.dataSource.getConnection();
    }
}
