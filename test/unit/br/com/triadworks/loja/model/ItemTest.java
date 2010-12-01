package br.com.triadworks.loja.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;


public class ItemTest {

	private Produto produto;
	
	@Before
	public void setup() {
		produto = new Produto();
		produto.setCodigo(3673L);
		produto.setNome("Livro de Auto-Ajuda");
		produto.setPreco(new BigDecimal("10.90"));
	}
	
	@Test
	public void deveriaRetornarValorDeAcordoComQuantidade() {
		assertEquals(new BigDecimal("10.90"), new Item(produto, 1).getValor());
		assertEquals(new BigDecimal("21.80"), new Item(produto, 2).getValor());
		assertEquals(new BigDecimal("109.00"), new Item(produto, 10).getValor());
		assertEquals(new BigDecimal("752.10"), new Item(produto, 69).getValor());
		assertEquals(new BigDecimal("1090.00"), new Item(produto, 100).getValor());
	}
	
	@Test
	public void deveriaInstaciarItemCorretamente() {
		Item item = new Item(produto, 1);
		assertEquals(1, item.getQuantidade());
		assertEquals(produto, item.getProduto());
		assertEquals(new BigDecimal("10.90"), item.getValor());
	}
	
}
