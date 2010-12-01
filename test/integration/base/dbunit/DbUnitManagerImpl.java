package base.dbunit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dbUnitManager")
public class DbUnitManagerImpl implements DbUnitManager {

	public static final String XML_COM_DADOS_BASICOS = "";
	DataSource dataSource;

	@Autowired
	public DbUnitManagerImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Connection getConnection() {
		Connection conn;
		try {
			conn = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Erro ao tentar obter uma conexão com o banco: "
							+ e.getMessage());
		}
		return conn;
	}

	public void refresh(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void cleanAndInsert(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void insert(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.INSERT.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void update(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.UPDATE.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delete(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.DELETE.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void deleteAll(String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.DELETE_ALL.execute(dbconn, this
					.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void clear() {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, getDataSetFrom(XML_COM_DADOS_BASICOS));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private IDataSet getDataSetFrom(String dbUnitXmlPath) throws IOException,
			DataSetException, FileNotFoundException {
		return new FlatXmlDataSetBuilder().build(new FileInputStream(dbUnitXmlPath));
	}

	private IDatabaseConnection getDbUnitConnection()
			throws DatabaseUnitException, SQLException {
		IDatabaseConnection dbconn = new DatabaseConnection(this.getConnection());
		dbconn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
		return dbconn;
	}

	public void dump(String dbUnitXmlPath) {
		try {
			IDatabaseConnection connection = new DatabaseConnection(
					getConnection());
			IDataSet iDataSet = connection.createDataSet();
			FlatXmlDataSet.write(iDataSet, new FileOutputStream(dbUnitXmlPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
