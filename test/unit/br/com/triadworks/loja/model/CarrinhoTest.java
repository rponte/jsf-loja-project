package br.com.triadworks.loja.model;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class CarrinhoTest {

	Carrinho carrinho;
	
	private Produto livro;
	private Produto ps3;
	
	@Before
	public void setup() {
		carrinho = new Carrinho();
		livro = criaProdutoCom("Domain-Driven Design Book", new BigDecimal("42.99"));
		ps3 = criaProdutoCom("Ps3", new BigDecimal("2000.00"));
	}
	
	@Test
	public void deveriaNaoPossuiItensInicialmente() {
		assertTrue("itens", carrinho.getItens().isEmpty());
		assertEquals("total de itens", 0, carrinho.getTotalDeItens());
		assertEquals("total do carrinho", BigDecimal.ZERO, carrinho.getTotal());
	}
	
	@Test
	public void deveriaAdicionarItemAoCarrinho() {
		
		carrinho.adiciona(new Item(livro, 2));
		carrinho.adiciona(new Item(ps3, 1));
		
		assertEquals("total de itens", 2, carrinho.getTotalDeItens());
		assertEquals("total do carrinho", new BigDecimal("2085.98"), carrinho.getTotal());
	}
	
	@Test
	public void deveriaRemoverItemDoCarrinho() {
		
		carrinho.adiciona(new Item(livro, 2));
		carrinho.adiciona(new Item(ps3, 1));
		
		Item item = carrinho.remove(1);
		
		assertEquals("produto do item removido", ps3, item.getProduto());
		assertEquals("total de itens", 1, carrinho.getTotalDeItens());
		assertEquals("total do carrinho", new BigDecimal("85.98"), carrinho.getTotal());
	}
	
	@Test
	public void deveriaAtualizarQuantidadeDoProdutoNoCarrinho() {
		// dado que
		carrinho.adiciona(new Item(livro, 10));
		// quando
		carrinho.atualiza(0, 7);
		// entao
		assertEquals("total de itens", 1, carrinho.getTotalDeItens());
		assertEquals("total do carrinho", new BigDecimal("300.93"), carrinho.getTotal());
	}

	private Produto criaProdutoCom(String nome, BigDecimal preco) {
		Produto produto = new Produto();
		produto.setCodigo(System.currentTimeMillis()); // gera Id qualquer
		produto.setNome(nome);
		produto.setPreco(preco);
		return produto;
	}
	
}
