package br.com.triadworks.loja.model;

import java.math.BigDecimal;

public class Item {

	private Produto produto;
	private int quantidade;
	
	public Item(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return produto.getPreco().multiply(new BigDecimal(quantidade));
	}

	
}
