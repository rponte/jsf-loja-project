package base.dbunit.vendors;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlConnection;

import base.dbunit.DefaultDbUnitManagerImpl;

public class MySqlDbUnitManagerImpl extends DefaultDbUnitManagerImpl {

	public MySqlDbUnitManagerImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	protected IDatabaseConnection getDbUnitConnection()
			throws DatabaseUnitException, SQLException {
		DatabaseMetaData databaseMetaData = getConnection().getMetaData();
		
		IDatabaseConnection dbconn = new MySqlConnection(getConnection(), databaseMetaData.getUserName());
		return dbconn;
	}

}
