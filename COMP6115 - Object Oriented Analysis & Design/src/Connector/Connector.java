package Connector;
import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Connector {
	
	private static Connection connection;
	

	public static Connection connect() {
		if(connection == null) {
			MysqlDataSource source = new MysqlDataSource();
			source.setServerName("localhost");
			source.setUser("root");
			source.setPassword("");
			source.setDatabaseName("justduit");
			
			try {
				return source.getConnection();
			}catch(SQLException e) {
				return null;
			}
		}
		return connection;
	}

}
