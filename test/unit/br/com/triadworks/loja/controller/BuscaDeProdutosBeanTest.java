package br.com.triadworks.loja.controller;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.triadworks.loja.service.ProdutoService;

public class BuscaDeProdutosBeanTest {

	BuscaDeProdutosBean controller;
	@Mock
	ProdutoService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new BuscaDeProdutosBean(service);
	}
	
	@Test
	public void deveriaBuscarProdutoPorNome() {
		// given
		dadoQueUsuarioEstaBuscandoPor("DDD Book");
		// when
		controller.busca();
		// then
		verify(service, atLeastOnce()).busca("DDD Book");
	}

	private void dadoQueUsuarioEstaBuscandoPor(String nome) {
		controller.setNome(nome);
	}
	
}
