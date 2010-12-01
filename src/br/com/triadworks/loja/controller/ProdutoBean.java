package br.com.triadworks.loja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.triadworks.loja.model.Produto;
import br.com.triadworks.loja.service.ProdutoService;
import br.com.triadworks.loja.util.FacesUtils;

@Component
@Scope("request")
public class ProdutoBean {

	private Produto produto = new Produto();
	private List<Produto> produtos = new ArrayList<Produto>();
	
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";
	private static final String ESTADO_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	
	private String state = ESTADO_DE_PESQUISA;
	
	private UIForm form;
	
	private final ProdutoService produtoService;
	private final FacesUtils facesUtils;
	
	@Autowired
	public ProdutoBean(ProdutoService produtoService, FacesUtils facesUtils) {
		this.produtoService = produtoService;
		this.facesUtils = facesUtils;
	}

	public void lista() {
		produtos = produtoService.listaTudo();
		setState(ESTADO_DE_PESQUISA);
	}
	
	public void preparaParaAdicionar() {
		limpa();
		setState(ESTADO_NOVO);
	}
	
	public void adiciona() {
		produtoService.adiciona(produto);
		facesUtils.adicionaMensagemDeInformacao("Produto adicionado com sucesso!");
		lista();
	}
	
	public void remove() {
		produtoService.remove(produto);
		facesUtils.adicionaMensagemDeInformacao("Produto removido com sucesso!");
		lista();
	}
	
	public void preparaParaAlterar() {
		this.produto = produtoService.carrega(produto.getCodigo()); // evita LazyInitializationException
		setState(ESTADO_DE_EDICAO);
	}
	
	public void altera() {
		produtoService.atualiza(produto);
		facesUtils.adicionaMensagemDeInformacao("Produto alterado com sucesso!");
		lista();
	}
	
	public void volta() {
		limpa();
		setState(ESTADO_DE_PESQUISA);
	}

	private void limpa() {
		produto = new Produto();
		facesUtils.cleanSubmittedValues(form);
	}
	
	public boolean isPesquisando() {
		return ESTADO_DE_PESQUISA.equals(state);
	}
	public boolean isEditando() {
		return ESTADO_DE_EDICAO.equals(state);
	}
	public boolean isAdicionando() {
		return ESTADO_NOVO.equals(state);
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public UIForm getForm() {
		return form;
	}
	public void setForm(UIForm form) {
		this.form = form;
	}
	
}
