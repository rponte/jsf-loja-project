package base.dbunit;

import static org.dbunit.operation.DatabaseOperation.*;

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
	
	/**
	 * Executa operações do DBUnit no dataset <code>dbUnitXmlPath</code>.
	 */
	protected void execute(DatabaseOperation operation, String dbUnitXmlPath) {
		try {
			IDatabaseConnection dbconn = this.getDbUnitConnection();
			operation.execute(dbconn, this.getDataSetFrom(dbUnitXmlPath));
			dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void refresh(String dbUnitXmlPath) {
		execute(REFRESH, dbUnitXmlPath);
	}

	public void cleanAndInsert(String dbUnitXmlPath) {
		execute(CLEAN_INSERT, dbUnitXmlPath);
	}

	public void insert(String dbUnitXmlPath) {
		execute(INSERT, dbUnitXmlPath);
	}

	public void update(String dbUnitXmlPath) {
		execute(UPDATE, dbUnitXmlPath);
	}

	public void delete(String dbUnitXmlPath) {
		execute(DELETE, dbUnitXmlPath);
	}

	public void deleteAll(String dbUnitXmlPath) {
		execute(DELETE_ALL, dbUnitXmlPath);
	}

	public void clear() {
		execute(CLEAN_INSERT, XML_COM_DADOS_BASICOS);
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
