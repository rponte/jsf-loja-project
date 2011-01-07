package br.com.triadworks.loja.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import base.dbunit.DbUnitManager;
import br.com.triadworks.loja.model.Produto;
import br.com.triadworks.loja.service.ProdutoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:./WebContent/WEB-INF/config/spring/applicationContext.xml",
								   "file:./WebContent/WEB-INF/config/spring/applicationContext-persistence-test.xml"})
@Transactional
public class ProdutoServiceImplTest {

	private static final String dbUnitDataSet = "test/integration/base/dbunit/xml/ProdutoServiceImplTest.xml";

	private static final Long ID_VALIDO = 9997L;
	private static final Long ID_INVALIDO = -1L;
	
	@Autowired
	DbUnitManager dbUnitManager;
	@Autowired
	ProdutoService service;
	
	@Before
	public void setup() {
		dbUnitManager.cleanAndInsert(dbUnitDataSet);
	}
	
	@Test
	public void deveriaEncontrarNenhumProdutoQuandoBuscaForVaziaOuNula() {
		assertTrue("busca vazia", service.busca("").isEmpty());
		assertTrue("busca nula", service.busca(null).isEmpty());
	}
	
	@Test
	public void deveriaBuscarProdutosPorNomeSemCaseSensitive() {
		List<Produto> produtos = service.busca("boNeCo");
		assertEquals("numero de registros encontrados", 1, produtos.size());
		verificaProduto(produtos.get(0), "Boneco do Comandos em Ação", "Boneco do Comandos em Ação", new BigDecimal("49.90"));
	}
	
	@Test
	public void deveriaBuscarProdutosPorNome() {
		List<Produto> produtos = service.busca("Domain-Driven Design");
		assertEquals("numero de registros encontrados", 1, produtos.size());
		verificaProduto(produtos.get(0), "Livro Domain-Driven Design", "Livro Domain-Driven Design", new BigDecimal("89.99"));
	}
	
	@Test
	public void deveriaBuscarPorId() {
		assertNull("não encontra produto", service.carrega(ID_INVALIDO));
		assertNotNull("encontra produto", service.carrega(ID_VALIDO));
	}
	
	@Test
	public void deveriaRemover() {
		// dado que
		Produto produto = service.carrega(ID_VALIDO);
		// quando
		service.remove(produto);
		// entao
		produto = service.carrega(ID_VALIDO);
		assertNull("produto removido", produto);
	}
	
	@Test
	public void deveriaAtualizar() {
		// dado que
		Produto produto = service.carrega(ID_VALIDO);
		verificaProduto(produto, "Livro Domain-Driven Design", "Livro Domain-Driven Design", new BigDecimal("89.99"));
		// quando
		produto.setNome("DDD Book");
		service.atualiza(produto);
		// entao
		produto = service.carrega(ID_VALIDO);
		verificaProduto(produto, "DDD Book", "Livro Domain-Driven Design", new BigDecimal("89.99"));
	}
	
	@Test
	public void deveriaInserir() {
		// dado que
		Produto novo = new Produto();
		novo.setNome("Novo Produto");
		novo.setDescricao("Novo Produto 2010");
		novo.setPreco(BigDecimal.TEN);
		// quando
		service.adiciona(novo);
		// entao
		Produto encontrado = service.carrega(novo.getCodigo());
		assertNotNull("produto", encontrado);
		verificaProduto(encontrado, "Novo Produto", "Novo Produto 2010", BigDecimal.TEN);
	}
	
	@Test
	public void deveriaListarTudo() {
		List<Produto> todos = service.listaTudo();
		
		assertEquals("numero de registros encontrados", 3, todos.size());
		
		verificaProduto(todos.get(0), "Livro Domain-Driven Design", "Livro Domain-Driven Design", new BigDecimal("89.99"));
		verificaProduto(todos.get(1), "Boneco do Comandos em Ação", "Boneco do Comandos em Ação", new BigDecimal("49.90"));
		verificaProduto(todos.get(2), "TV de Plasma 42'", "TV de Plasma 42' - HD", new BigDecimal("4299.00"));
	}

	private void verificaProduto(Produto produto, String nome, String descricao, BigDecimal preco) {
		assertEquals("nome", nome, produto.getNome());
		assertEquals("descricao", descricao, produto.getDescricao());
		assertEquals("preco", preco, produto.getPreco());
	}
	
}
