package com.watanabe.restful.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.watanabe.restful.domain.Cliente;

public class ClienteResourceMock implements IClienteResource{
	
	private static final Map<Long,Cliente> CLIENTE_DB = new HashMap<Long, Cliente>();
	private static final AtomicLong COUNTER = new AtomicLong();
	
	@Override
	public List<Cliente> getAllClientes(){
		return new ArrayList<Cliente>(CLIENTE_DB.values());
	}
	
	@Override
	public Cliente getCliente(Long id) {
		return CLIENTE_DB.get(id);
	}
	
	@Override
	public void deleteCliente(Long id) {
		CLIENTE_DB.remove(id);
	}
	
	@Override
	public Cliente insertCliente(Cliente cliente) {
		cliente.id = COUNTER.incrementAndGet();
		return CLIENTE_DB.put(cliente.id, cliente);
	}
}
