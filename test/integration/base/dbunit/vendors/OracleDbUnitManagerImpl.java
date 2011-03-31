package base.dbunit.vendors;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleConnection;

import base.dbunit.DefaultDbUnitManagerImpl;

public class OracleDbUnitManagerImpl extends DefaultDbUnitManagerImpl {

	public OracleDbUnitManagerImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	protected IDatabaseConnection getDbUnitConnection()
			throws DatabaseUnitException, SQLException {
		IDatabaseConnection dbconn;
		DatabaseMetaData databaseMetaData = this.getConnection().getMetaData();

		// oracle schema name is the user name
		dbconn = new OracleConnection(this.getConnection(), databaseMetaData.getUserName());
		DatabaseConfig config = dbconn.getConfig();
		// oracle 10g
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
		// receycle bin (skip oracle 10g recycle bin system tables if enabled)
		config.setProperty(DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, Boolean.TRUE);

		return dbconn;
	}

}
