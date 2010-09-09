package br.com.triadworks.loja.util;

import javax.faces.context.FacesContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class FacesUtilsTest {

	FacesUtils facesUtils;
	
	@Before
	public void setup() {
		FacesContext facesContext = new MockFacesContext();
		facesUtils = new FacesUtils(facesContext);
	}
	
	@Test
	public void deveriaNaoPossuirMensagem() {
		Assert.assertFalse(facesUtils.possuiMensagem("erro"));
	}
	
	@Test
	public void deveriaAdicionarMensagemDeErro() {
		facesUtils.adicionaMensagemDeErro("erro");
		Assert.assertTrue(facesUtils.possuiMensagem("erro"));
	}
	
	@Test
	public void deveriaAdicionarMensagemDeInformacao() {
		facesUtils.adicionaMensagemDeInformacao("info");
		Assert.assertTrue(facesUtils.possuiMensagem("info"));
	}
}
