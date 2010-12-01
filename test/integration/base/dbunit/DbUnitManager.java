package base.dbunit;

import java.sql.Connection;

public interface DbUnitManager {

	/**
	 * Atualiza o banco com os dados do arquivo xml, porém não altera os
	 * registros anteriormente inseridos no banco e que não existem no arquivo
	 * xml.
	 */
	public abstract void refresh(String dbUnitXmlPath);

	/**
	 * Deleta todos os dados de cada tabela e em seguida insere os registros encontrados no arquivo xml.
	 */
	public abstract void cleanAndInsert(String dbUnitXmlPath);

	/**
	 * Insere os dados encontrados no arquivo xml.
	 */
	public abstract void insert(String dbUnitXmlPath);

	/**
	 * Atualiza os registros encontrados no arquivo xml.
	 */
	public abstract void update(String dbUnitXmlPath);

	/**
	 * Deleta os registros encontrados no arquivo xml.
	 */
	public abstract void delete(String dbUnitXmlPath);

	/**
	 * Deleta todos os dados de cada tabela encontrada no arquivo xml.
	 */
	public abstract void deleteAll(String dbUnitXmlPath);

	/**
	 * Limpa o banco e popula apenas com os dados básicos.
	 */
	public abstract void clear();

	/**
	 * Gera dataset do banco de dados.
	 */
	public abstract void dump(String dbUnitXmlPath);

	public Connection getConnection();

}