package base.dbunit.vendors;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mssql.MsSqlConnection;
import org.dbunit.operation.DatabaseOperation;

import base.dbunit.DefaultDbUnitManagerImpl;

public class MSSqlServerDbUnitManagerImpl extends DefaultDbUnitManagerImpl {

	public MSSqlServerDbUnitManagerImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	protected void execute(DatabaseOperation operation, String dbUnitXmlPath) {
		operation = new InsertIdentityOperation(operation);
		super.execute(operation, dbUnitXmlPath);
	}
	
	@Override
	protected IDatabaseConnection getDbUnitConnection()
			throws DatabaseUnitException, SQLException {
		IDatabaseConnection dbconn = new MsSqlConnection(getConnection());
		return dbconn;
	}

}
