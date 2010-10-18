package br.com.triadworks.loja.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.triadworks.loja.model.Produto;
import br.com.triadworks.loja.service.ProdutoService;

@Component
@Scope("request")
public class BuscaDeProdutosBean {
	
	private String nome;
	private List<Produto> resultado = new ArrayList<Produto>();
	
	private final ProdutoService produtoService;

	@Autowired
	public BuscaDeProdutosBean(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	/**
	 * Busca produto por nome.
	 */
	public void busca() {
		resultado = produtoService.busca(nome);
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Produto> getResultado() {
		return resultado;
	}
	public void setResultado(List<Produto> resultado) {
		this.resultado = resultado;
	}
	
}
