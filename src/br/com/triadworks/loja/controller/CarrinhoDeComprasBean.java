package br.com.triadworks.loja.controller;

import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.triadworks.loja.model.Carrinho;
import br.com.triadworks.loja.model.Item;
import br.com.triadworks.loja.model.Produto;

@Component
@Scope("request")
public class CarrinhoDeComprasBean {

	private Produto produto;
	private int quantidade = 1;
	private int indiceDoItem;
	
	private HtmlDataTable tblItens;
	
	private Carrinho carrinho;
	
	@Autowired
	public CarrinhoDeComprasBean(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	public String adiciona() {
		Item item = new Item(produto, quantidade);
		carrinho.adiciona(item);
		return "carrinhoDeCompras";
	}
	
	public String remove() {
		carrinho.remove(indiceDoItem);
		return "carrinhoDeCompras";
	}
	
	public void alteraQuantidade(ValueChangeEvent event) {
		int index = tblItens.getRowIndex();
		Integer quantidade = (Integer) event.getNewValue();
		if (quantidade != null)
			carrinho.atualiza(index, quantidade);
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getIndiceDoItem() {
		return indiceDoItem;
	}
	public void setIndiceDoItem(int indiceDoItem) {
		this.indiceDoItem = indiceDoItem;
	}
	public HtmlDataTable getTblItens() {
		return tblItens;
	}
	public void setTblItens(HtmlDataTable tblItens) {
		this.tblItens = tblItens;
	}
	
}
