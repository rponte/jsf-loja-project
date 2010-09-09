package br.com.triadworks.loja.controller;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.triadworks.loja.model.Usuario;


public class UsuarioWebTest {

	UsuarioWeb usuarioWeb;
	
	@Before
	public void setup() {
		usuarioWeb = new UsuarioWeb();
	}
	
	@Test
	public void deveriaEfetuarLogin() {
		Usuario user = new Usuario("user", "secret");
		
		usuarioWeb.loga(user);
		
		Assert.assertNotNull(usuarioWeb.getUsuario());
		Assert.assertTrue(usuarioWeb.isLogado());
	}
	
	@Test
	public void deveriaEfetuarLogout() {
		seExisteUsuarioLogado();
		
		usuarioWeb.logout();
		
		Assert.assertNull(usuarioWeb.getUsuario());
		Assert.assertFalse(usuarioWeb.isLogado());
	}

	private void seExisteUsuarioLogado() {
		Usuario user = new Usuario("user", "secret");
		usuarioWeb.loga(user);		
	}
	
}
