package br.com.triadworks.loja.service;

import java.util.List;

import br.com.triadworks.loja.model.Produto;

public interface ProdutoService {

	public List<Produto> listaTudo();

	public void adiciona(Produto produto);

	public void remove(Produto produto);

	public Produto carrega(Long codigo);

	public void atualiza(Produto produto);

	public List<Produto> busca(String nome);

}
