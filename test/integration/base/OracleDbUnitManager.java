package base;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;

public class OracleDbUnitManager extends DbUnitManager {

	public OracleDbUnitManager(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public IDatabaseConnection getDbUnitConnection() throws DatabaseUnitException, SQLException {
		
		DatabaseMetaData databaseMetaData = this.getConnection().getMetaData();
		IDatabaseConnection dbconn = new DatabaseConnection(getConnection(), databaseMetaData.getUserName().toUpperCase());
		
		DatabaseConfig config = dbconn.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory()); // oracle 10g
		config.setProperty(DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, Boolean.TRUE); // ignore receycle bin
		
		return dbconn;
	}

}
