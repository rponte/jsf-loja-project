package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;


public abstract class DbUnitManager {

	public static final String XML_COM_DADOS_BASICOS = "";
	
	DataSource dataSource;

	public DbUnitManager(DataSource dataSource) {
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
	 * Atualiza o banco com os dados do arquivo xml, porém não altera os
	 * registros anteriormente inseridos no banco e que não existem no arquivo
	 * xml.
	 */
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
	/**
	 * Deleta todos os dados de cada tabela e em seguida insere os registros encontrados no arquivo xml.
	 */
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
	/**
	 * Insere os dados encontrados no arquivo xml.
	 */
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
	/**
	 * Atualiza os registros encontrados no arquivo xml.
	 */
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
	/**
	 * Deleta os registros encontrados no arquivo xml.
	 */
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
	/**
	 * Deleta todos os dados de cada tabela encontrada no arquivo xml.
	 */
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
	/**
	 * Limpa o banco e popula apenas com os dados básicos.
	 */
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
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
//		builder.setColumnSensing(true);
		IDataSet dataSet = builder.build(new FileInputStream(dbUnitXmlPath));
		return dataSet;
	}

	/**
	 * Gera o dump do banco e salva o dataset em <code>dbUnitXmlPath</code>.
	 */
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
	
	/**
	 * Deve ser implementado de acordo com o tipo de banco de dados.
	 */
	public abstract IDatabaseConnection getDbUnitConnection() throws DatabaseUnitException, SQLException;

}
