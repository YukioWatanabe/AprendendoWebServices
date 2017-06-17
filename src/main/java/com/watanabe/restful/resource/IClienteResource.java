package com.watanabe.restful.resource;

import java.util.List;

import com.watanabe.restful.domain.Cliente;

public interface IClienteResource {
	public List<Cliente> getAllClientes();
	public Cliente getCliente(Long id);
	public void deleteCliente(Long id);
	public Cliente insertCliente(Cliente cliente);
}
