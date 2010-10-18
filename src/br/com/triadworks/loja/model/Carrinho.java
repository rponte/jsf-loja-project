package br.com.triadworks.loja.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Carrinho {

	List<Item> itens = new ArrayList<Item>();
	BigDecimal total = BigDecimal.ZERO;
	
	public List<Item> getItens() {
		return Collections.unmodifiableList(itens);
	}

	public void adiciona(Item item) {
		itens.add(item);
		total = total.add(item.getValor());
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public int getTotalDeItens() {
		return itens.size();
	}

	public Item remove(int indiceDoItem) {
		Item item = itens.remove(indiceDoItem);
		total = total.subtract(item.getValor());
		return item;
	}

	public void atualiza(int indiceDoItem, int quantidade) {
		Item item = itens.get(indiceDoItem);
		item.setQuantidade(quantidade);
		recalcula();
	}

	private void recalcula() {
		total = new BigDecimal("0.0");
		for (Item item : itens)
			total = total.add(item.getValor());
	}
	
}
