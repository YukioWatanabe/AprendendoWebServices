package com.watanabe.restful.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.watanabe.restful.domain.Cliente;

public class ClienteResourceMock implements IClienteResource{
	
	private static final Map<Long,Cliente> CLIENTE_DB = new ConcurrentHashMap<Long, Cliente>();
	private static final AtomicLong COUNTER = new AtomicLong();
	
	public ClienteResourceMock() {
		Cliente cliente = new Cliente();
		cliente.setNome("Xpto");
		cliente.setSobrenome("de Xpto");
		
		Cliente cliente2 = new Cliente();
		cliente2.setNome("Xpto 2");
		cliente2.setSobrenome("de Xpto 2");
		
		cliente.setId(COUNTER.incrementAndGet());
		cliente2.setId(COUNTER.incrementAndGet());
		
		CLIENTE_DB.put(cliente.getId(), cliente);
		CLIENTE_DB.put(cliente2.getId(), cliente2);
	}
	
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
		cliente.setId(COUNTER.incrementAndGet());
		return CLIENTE_DB.put(cliente.getId(), cliente);
	}
	
	@Override
	public Cliente updateCliente(Cliente cliente) {
		return CLIENTE_DB.put(cliente.getId(), cliente);
	}

}
