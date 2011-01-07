package br.com.triadworks.loja.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.triadworks.loja.model.Carrinho;
import br.com.triadworks.loja.model.Item;
import br.com.triadworks.loja.model.Produto;


public class CarrinhoDeComprasBeanTest {

	CarrinhoDeComprasBean controller;
	Carrinho carrinho;
	
	private ValueChangeEvent event;
	@Mock
	private HtmlDataTable tblItens;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		carrinho = new Carrinho();
		controller = new CarrinhoDeComprasBean(carrinho);
	}
	
	@Test
	public void deveriaAdicionarProdutoAoCarrinho() {
		// given
		dadoQueUsuarioSelecionou(produto("Clean Code Book", noValorDe("89.90")), quantidade(2));
		// when
		controller.adiciona();
		// then
		assertEquals("valor total no carrinho", new BigDecimal("179.80"), carrinho.getTotal());
		assertEquals("total de itens no carrinho", 1, carrinho.getTotalDeItens());
	}
	
	@Test
	public void deveriaRemoverProdutoDoCarrinhoPorIndice() {
		// given
		dadoQueCarrinhoJaPossuiDoisProdutos();
		dadoQueUsuarioQueiraRemoverItemDoCarrinhoComIndice(1);
		// when
		controller.remove();
		// then
		assertEquals("valor total no carrinho", new BigDecimal("100.00"), carrinho.getTotal());
		assertEquals("total de itens no carrinho", 1, carrinho.getTotalDeItens());
	}
	
	@Test
	public void deveriaAlterarQuantidadeDeDeterminadoProdutoDoCarrinho() {
		// given
		dadoQueCarrinhoJaPossuiDoisProdutos();
		dadoQueUsuarioMudouQuantidadeDoSegundoProdutoPara(1);
		// when
		controller.alteraQuantidade(event);
		// then
		assertEquals("valor total no carrinho", new BigDecimal("150.00"), carrinho.getTotal());
		assertEquals("total de itens no carrinho", 2, carrinho.getTotalDeItens());
	}
	
	@Test
	public void deveriaSempreInstanciarControllerComQuantidadeIgualAUm() {
		assertEquals("quantidade inicial", 1, controller.getQuantidade());
	}

	private void dadoQueUsuarioMudouQuantidadeDoSegundoProdutoPara(int nova) {
		// index do item selecionado na tabela
		when(tblItens.getRowIndex()).thenReturn(1);
		controller.setTblItens(tblItens);
		// dispara evento onchange
		event = new ValueChangeEvent(new UIInput(), 2, nova);
	}

	private void dadoQueUsuarioQueiraRemoverItemDoCarrinhoComIndice(int indice) {
		controller.setIndiceDoItem(indice);
	}

	private void dadoQueCarrinhoJaPossuiDoisProdutos() {
		carrinho.adiciona(new Item(produto("DDD Book", noValorDe("100.00")), 1));
		carrinho.adiciona(new Item(produto("Fone de ouvido", noValorDe("50.00")), 2));
	}

	private void dadoQueUsuarioSelecionou(Produto produto, int quantidade) {
		controller.setProduto(produto);
		controller.setQuantidade(quantidade);
	}

	private int quantidade(int quantidade) {
		return quantidade;
	}
	
	private BigDecimal noValorDe(String valor) {
		return new BigDecimal(valor);
	}

	private Produto produto(String nome, BigDecimal preco) {
		Produto produto = new Produto();
		produto.setCodigo(System.currentTimeMillis()); // gera Id qualquer
		produto.setNome(nome);
		produto.setDescricao(nome);
		produto.setPreco(preco);
		return produto;
	}
}
