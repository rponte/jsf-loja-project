package base.dbunit.vendors;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import base.dbunit.DefaultDbUnitManagerImpl;

public class PostgresqlDbUnitManagerImpl extends DefaultDbUnitManagerImpl {

	public PostgresqlDbUnitManagerImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected IDatabaseConnection getDbUnitConnection()
			throws DatabaseUnitException, SQLException {
		IDatabaseConnection dbconn = new DatabaseConnection(getConnection());
		dbconn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
		return dbconn;
	}
	
}
