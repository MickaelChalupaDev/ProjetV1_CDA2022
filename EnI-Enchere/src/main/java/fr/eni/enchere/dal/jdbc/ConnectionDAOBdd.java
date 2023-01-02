package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
