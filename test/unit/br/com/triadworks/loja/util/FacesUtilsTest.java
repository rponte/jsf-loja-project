package br.com.triadworks.loja.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
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
	
	@Test
	public void deveriaLimparInputsNaArvoreDeComponentes() {
		// dado que
		UIForm form = new UIForm();
		form.getChildren().add(criaUIInputSujoQualquer());
		// quando
		facesUtils.cleanSubmittedValues(form);
		// entao
		assertEquals("form ainda com componente filho", 1, form.getChildren().size());
		assertQueComponenteFilhoEstejaLimpo(form.getChildren().get(0));
	}

	private void assertQueComponenteFilhoEstejaLimpo(UIComponent uiComponent) {
		UIInput input = (UIInput) uiComponent;
		// propriedas que deveriam ser limpas
		assertNull("submitted value", input.getSubmittedValue());
		assertNull("local value", input.getValue());
		assertFalse("local value set", input.isLocalValueSet());
		assertTrue("valid", input.isValid());
		// propriedades que deveriam continuar com mesmo estado
		assertTrue("required", input.isRequired());
		assertTrue("rendered", input.isRendered());
	}

	/**
	 * UIInput criado com estado sujo (e provavelmente inconsistente) apenas
	 * para intuito de testes.
	 */
	private UIInput criaUIInputSujoQualquer() {
		UIInput input = new UIInput();
		input.setSubmittedValue("12.90");
		input.setValue(new BigDecimal("12.90"));
		input.setLocalValueSet(true);
		input.setValid(false);
		input.setRequired(true); // pra constar que nao foi alterado
		input.setRendered(true); // pra constar que nao foi alterado
		return input;
	}
	
}
