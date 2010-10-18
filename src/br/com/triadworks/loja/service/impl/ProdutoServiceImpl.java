package br.com.triadworks.loja.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import br.com.triadworks.loja.model.Produto;
import br.com.triadworks.loja.service.ProdutoService;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Produto> listaTudo() {
		return entityManager.createQuery("from Produto").getResultList();
	}
	
	public void adiciona(Produto produto) {
		entityManager.persist(produto);
	}

	public void altera(Produto produto) {
		entityManager.merge(produto);
	}

	public Produto carrega(Long codigo) {
		return entityManager.find(Produto.class, codigo);
	}

	public void remove(Produto produto) {
		entityManager.remove(entityManager.merge(produto));
	}

	@SuppressWarnings("unchecked")
	public List<Produto> busca(String nome) {
		if (nome == null ||
				"".equals(nome)) {
			return Collections.EMPTY_LIST;
		}
		return entityManager
			.createQuery("from Produto p where lower(p.nome) like lower(:nome)")
			.setParameter("nome", "%" + nome + "%")
			.getResultList();
	}

}
