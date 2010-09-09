package br.com.triadworks.loja.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIForm;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.triadworks.loja.model.Produto;
import br.com.triadworks.loja.service.ProdutoService;
import br.com.triadworks.loja.util.FacesUtils;


public class ProdutoBeanTest {

	ProdutoBean controller;
	@Mock
	ProdutoService produtoService;
	@Mock
	FacesUtils facesUtils;
	
	private Produto produto;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ProdutoBean(produtoService, facesUtils);
	}
	
	@Test
	public void deveriaVoltarParaPesquisa() {
		
		seExisteUmUiFormBindado();
		
		controller.volta();
		
		Assert.assertTrue(controller.isPesquisando());
	}
	
	@Test
	public void deveriaPrepararParaAlterar() {
		
		seExisteUmUiFormBindado();
		seProdutoSelecionadoFoi(7L);
		seExisteProdutoCadastradoComCodigo(7L);
		
		controller.preparaParaAlterar();
		
		Assert.assertNotNull(controller.getProduto());
		Assert.assertTrue(controller.getForm().getChildren().isEmpty());
		Assert.assertTrue(controller.isEditando());
	}

	private void seExisteProdutoCadastradoComCodigo(Long codigo) {
		when(produtoService.carrega(codigo)).thenReturn(new Produto());
	}
	
	@Test
	public void deveriaPrepararParaAdicionar() {
		
		seExisteUmUiFormBindado();
		
		controller.preparaParaAdicionar();
		
		Assert.assertNotNull(controller.getProduto());
		Assert.assertTrue(controller.getForm().getChildren().isEmpty());
		Assert.assertTrue(controller.isAdicionando());
	}
	
	private void seExisteUmUiFormBindado() {
		controller.setForm(new UIForm());		
	}

	@Test
	public void deveriaAlterarProduto() {
		seProdutoSelecionadoFoi(7L);
		
		controller.altera();
		
		verify(produtoService).altera(controller.getProduto());
		verify(facesUtils).adicionaMensagemDeInformacao("Produto alterado com sucesso!");
		Assert.assertTrue("pesquisando", controller.isPesquisando());
	}
	
	@Test
	public void deveriaRemoverProduto() {
		seProdutoSelecionadoFoi(7L);
		
		controller.remove();
		
		verify(produtoService).remove(controller.getProduto());
		verify(facesUtils).adicionaMensagemDeInformacao("Produto removido com sucesso!");
		Assert.assertTrue("pesquisando", controller.isPesquisando());
	}
	
	private void seProdutoSelecionadoFoi(long codigo) {
		produto = new Produto();
		produto.setCodigo(codigo);
		controller.setProduto(produto);
	}

	@Test
	public void deveriaAdicionarNovoProduto() {
		
		seUsuarioEntrouComProduto("Doritos", "Doritos 500g", new BigDecimal("4.60"));
		
		controller.adiciona();
		
		verify(produtoService).adiciona(controller.getProduto());
		verify(facesUtils).adicionaMensagemDeInformacao("Produto adicionado com sucesso!");
		Assert.assertTrue("pesquisando", controller.isPesquisando());
	}
	
	private void seUsuarioEntrouComProduto(String nome, String descricao, BigDecimal preco) {
		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPreco(preco);
		
		controller.setProduto(produto);
	}

	@Test
	public void deveriaListarTudo() {
		seExistemQuatroProdutosCadastrados();
		
		controller.lista();
		
		Assert.assertNotNull(controller.getProdutos());
		Assert.assertEquals(4, controller.getProdutos().size());
		Assert.assertTrue(controller.isPesquisando());
	}

	private void seExistemQuatroProdutosCadastrados() {
		List<Produto> produtos = Arrays.asList(new Produto(), new Produto(), new Produto(), new Produto());
		when(produtoService.listaTudo()).thenReturn(produtos);
	}
	
}
