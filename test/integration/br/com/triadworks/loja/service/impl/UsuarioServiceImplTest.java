package br.com.triadworks.loja.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import base.dbunit.DbUnitManager;
import br.com.triadworks.loja.model.Usuario;
import br.com.triadworks.loja.service.UsuarioService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:./WebContent/WEB-INF/config/spring/applicationContext.xml",
								   "file:./WebContent/WEB-INF/config/spring/applicationContext-persistence-test.xml"})
@Transactional
public class UsuarioServiceImplTest {

	private static final String dbUnitDataSet = "test/integration/base/dbunit/xml/UsuarioServiceImplTest.xml";
	
	@Autowired
	DbUnitManager dbUnitManager;
	@Autowired
	UsuarioService service;
	
	@Before
	public void setup() {
		dbUnitManager.cleanAndInsert(dbUnitDataSet);
	}
	
	@Test
	public void deveriaAutenticarUsuario() {
		Usuario usuario = service.autentica("rponte", "secret");
		
		assertNotNull("Usuário encontrado", usuario);
		assertEquals("login","rponte", usuario.getLogin());
		assertEquals("nome","Rafael Ponte", usuario.getNome());
	}
	
	@Test
	public void deveriaNaoAutenticarUsuario() {
		Usuario usuario = service.autentica("invalid", "user");
		assertNull("Usuário encontrado", usuario);
	}
	
}
