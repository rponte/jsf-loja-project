package br.com.triadworks.loja.service;

import br.com.triadworks.loja.model.Usuario;

public interface UsuarioService {

	public Usuario autentica(String login, String senha);

}
